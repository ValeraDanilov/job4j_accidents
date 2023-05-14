package ru.job4j.ru.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.ru.model.Accident;
import ru.job4j.ru.model.Rule;
import ru.job4j.ru.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;

    @GetMapping("/accident")
    public String accidents(Model model) {
        model.addAttribute("user", "Ira");
        return "accident";
    }

    @GetMapping("/formAccidentId/{id}")
    public String searchItem(Model model, @PathVariable Integer id) {
        model.addAttribute("accidents", this.accidents.findById(id));
        model.addAttribute("user", "Ira");
        return "accident";
    }

    @PostMapping("/createAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam(value = "type.id") Integer typeId,
                       HttpServletRequest req) {
        accident.setRules(getRules(req));
        accident.setType(this.accidents.findByIdType(typeId));
        accidents.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident/{accidentId}")
    public String formUpdateItem(Model model, @PathVariable("accidentId") int id) {
        model.addAttribute("accident", this.accidents.findById(id));
        model.addAttribute("types", this.accidents.findAllType());
        model.addAttribute("rules", this.accidents.findAllRules());
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String updateItem(@ModelAttribute Accident accident, @RequestParam(value = "type.id") Integer typeId,
                             HttpServletRequest req) {
        accident.setRules(getRules(req));
        accident.setType(this.accidents.findByIdType(typeId));
        this.accidents.update(accident);
        return String.format("redirect:/formAccidentId/%s", accident.getId());
    }

    private Set<Rule> getRules(HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        Arrays.stream(ids).forEach((a) -> rules.add(this.accidents.findByIdRule(Integer.parseInt(a))));
        return rules;
    }
}
