package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.model.Role;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;
import ru.job4j.accidents.service.UserService;

@Controller
@AllArgsConstructor
public class IndexController {
    private final AccidentService service;
    private final AccidentTypeService typeService;
    private final RuleService ruleService;
    private final UserService userService;

    @GetMapping("/index")
    public String index(Model model) {
        User user = this.userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("accidents", this.service.findAll());
        model.addAttribute("types", this.typeService.findAllAccidentsTyp());
        model.addAttribute("rules", this.ruleService.findAllRule());
        model.addAttribute("isAdmin", user != null && user.getRoles() != null && user.getRoles().contains(Role.ADMIN));
        model.addAttribute("user", user);
        return "index";
    }
}
