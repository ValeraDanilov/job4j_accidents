package ru.job4j.ru.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.ru.model.Accident;
import ru.job4j.ru.service.AccidentService;


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
    public String save(@ModelAttribute Accident accident) {
        accidents.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident/{accidentId}")
    public String formUpdateItem(Model model, @PathVariable("accidentId") int id) {
        model.addAttribute("accident", this.accidents.findById(id));
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String updateItem(@ModelAttribute Accident accident) {
        this.accidents.update(accident);
        return String.format("redirect:/formAccidentId/%s", accident.getId());
    }
}
