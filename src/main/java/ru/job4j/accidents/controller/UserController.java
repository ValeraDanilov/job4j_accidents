package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Role;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

import java.util.*;

@Controller
@AllArgsConstructor
public class UserController {


    private final PasswordEncoder encoder;
    private final UserService users;

    @GetMapping("/reg")
    public String getReg() {
        return "reg";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUsers(Model model) {
        model.addAttribute("users", this.users.findAll());
        model.addAttribute("roles", Role.values());
        return "users";
    }

    @PostMapping("/updateUser")
    public String userUpdate(@ModelAttribute User user) {
        Optional<User> findUser = this.users.findById(user.getId());
        if (findUser.isPresent()) {
            findUser.get().setUsername(user.getUsername());
            this.users.update(findUser.get());
        }
        return "redirect:/logout";
    }

    @GetMapping("/getUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable Integer id) {
        Optional<User> findUser = this.users.findById(id);
        if (findUser.isPresent()) {
            return findUser.get();
        }
        throw new NoSuchElementException();
    }

    @PostMapping("/userEdite")
    public String userEdite(@ModelAttribute User user) {
        Optional<User> findUser = this.users.findById(user.getId());
        if (findUser.isPresent()) {
            user.setPassword(findUser.get().getPassword());
            user.setEnabled(findUser.get().isEnabled());
            this.users.update(user);
        }
        return "redirect:/users";
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(this.encoder.encode(user.getPassword()));
        boolean isFlag = this.users.findAll().stream()
                .anyMatch(us -> user.getUsername().equalsIgnoreCase("admin"));
        if (!isFlag && user.getUsername().equalsIgnoreCase("admin")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        } else {
            user.setRoles(Collections.singleton(Role.USER));
        }
        this.users.update(user);
        return "redirect:/login";
    }


}
