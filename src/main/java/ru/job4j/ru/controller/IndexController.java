package ru.job4j.ru.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.ru.service.AccidentService;

@Controller
public class IndexController {

    private final AccidentService service;

    public IndexController(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Ira");
        model.addAttribute("accidents", this.service.findAll());
        model.addAttribute("types", this.service.findAllType());
        model.addAttribute("rules", this.service.findAllRules());
        return "index";
    }
}
