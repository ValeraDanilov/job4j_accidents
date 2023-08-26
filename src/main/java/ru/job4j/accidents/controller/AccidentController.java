package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;
    private final AccidentTypeService typeService;
    private final RuleService ruleService;

    @GetMapping("/accident")
    public String accidents(Model model) {
        model.addAttribute("user", "Ira");
        return "accident";
    }

    @GetMapping("/formAccidentId/{id}")
    public String searchItem(Model model, @PathVariable Integer id) {
        Optional<Accident> accident = this.accidents.findByAccidentId(id);
        if (accident.isPresent()) {
            model.addAttribute("accidents", accident.get());
            model.addAttribute("user", "Ira");
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
            this.accidents.create(accident);
        }
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident/{accidentId}")
    public String formUpdateItem(Model model, @PathVariable("accidentId") int id) {
        Optional<Accident> accident = this.accidents.findByAccidentId(id);
        if (accident.isPresent()) {
            model.addAttribute("accident", accident.get());
            model.addAttribute("types", this.typeService.findAllAccidentsTyp());
            model.addAttribute("rules", this.ruleService.findAllRule());
        }
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String updateItem(@ModelAttribute Accident accident, @RequestParam(value = "type.id") Integer typeId,
                             HttpServletRequest req) {
        Optional<AccidentType> accidentType = this.typeService.findByAccidentTypeId(typeId);
        if (accidentType.isPresent()) {
            accident.setRules(getRules(req));
            accident.setType(accidentType.get());
            this.accidents.update(accident);
        }
        return String.format("redirect:/formAccidentId/%s", accident.getId());
    }

    @GetMapping("/formDeleteAccident/{accidentId}")
    public String deleteAccident(@PathVariable("accidentId") int id) {
        Optional<Accident> accident = this.accidents.findByAccidentId(id);
        accident.ifPresent(this.accidents::delete);
        return "redirect:/index";
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
