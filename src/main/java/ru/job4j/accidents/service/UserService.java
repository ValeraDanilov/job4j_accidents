package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return (List<User>) this.repository.findAll();
    }

    public Optional<User> findById(int id) {
        return this.repository.findById(id);
    }

    public User findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public void update(User user) {
        this.repository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return findByUsername(username);
    }
}
