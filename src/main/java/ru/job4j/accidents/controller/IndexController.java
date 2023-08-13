package ru.job4j.accidents.controller;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

@Controller
@AllArgsConstructor
public class IndexController {
    private final AccidentService service;
    private final AccidentTypeService typeService;
    private final RuleService ruleService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("accidents", this.service.findAll());
        model.addAttribute("types", this.typeService.findAllAccidentsTyp());
        model.addAttribute("rules", this.ruleService.findAllRule());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "index";
    }
}
