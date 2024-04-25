package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentStatus;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;
import ru.job4j.accidents.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;
    private final AccidentTypeService typeService;
    private final RuleService ruleService;
    private final AtomicInteger accidentId = new AtomicInteger();
    private final UserService userService;
    private static final String INDEX = "redirect:/index";
    private static String status;
    private static final List<String> STATUS = Arrays.stream(AccidentStatus.values())
            .map(AccidentStatus::getValue).toList();


    @GetMapping("/formStatusCondition/{id}")
    public String changeStatusAccident(@PathVariable String id) {
        Optional<Accident> accident = this.accidents.findByAccidentId(this.accidentId.get());
        if (accident.isPresent()) {
            accident.get().setStatus(id);
            status = id;
            this.accidents.update(accident.get());
            return String.format("redirect:/formAccidentId/%s", accident.get().getId());
        }
        return INDEX;
    }

    @GetMapping("/formAccidentId/{id}")
    public String searchItem(Model model, @PathVariable Integer id) {
        Optional<Accident> findAccident = this.accidents.findByAccidentId(id);
        if (findAccident.isPresent()) {
            model.addAttribute("accidents", findAccident.get());
            model.addAttribute("chooses", STATUS);
            model.addAttribute("types", this.typeService.findAllAccidentsTyp());
            model.addAttribute("rules", this.ruleService.findAllRule());
            model.addAttribute("user", this.userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            model.addAttribute("accidentStatusId", findAccident.get().getStatus());
            this.accidentId.set(findAccident.get().getId());
        }
        return "accident";
    }

    @PostMapping("/createAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam(value = "type.id") Integer typeId,
                       HttpServletRequest req) {
        Optional<AccidentType> accidentType = this.typeService.findByAccidentTypeId(typeId);
        if (accidentType.isPresent()) {
            accident.setRules(getRules(req));
            accident.setType(accidentType.get());
            accident.setStatus("Accepted");
            this.accidents.create(accident);
        }
        return INDEX;
    }

    @PostMapping("/updateAccident")
    public String updateItem(@ModelAttribute Accident accident, @RequestParam(value = "type.id") Integer typeId,
                             HttpServletRequest req) {
        Optional<AccidentType> accidentType = this.typeService.findByAccidentTypeId(typeId);
        if (accidentType.isPresent()) {
            accident.setRules(getRules(req));
            accident.setType(accidentType.get());
            accident.setStatus(status);
            this.accidents.update(accident);
            return String.format("redirect:/formAccidentId/%s", accident.getId());
        }
        return INDEX;
    }

    @GetMapping("/formDeleteAccident/{accidentId}")
    public String deleteAccident(@PathVariable("accidentId") int id) {
        Optional<Accident> accident = this.accidents.findByAccidentId(id);
        accident.ifPresent(this.accidents::delete);
        return INDEX;
    }

    private Set<Rule> getRules(HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        for (String st : ids) {
            Optional<Rule> rule = this.ruleService.findByRuleId(Integer.parseInt(st));
            rule.ifPresent(rules::add);
        }
        return rules;
    }
}
