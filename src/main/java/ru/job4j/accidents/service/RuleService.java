package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleHibernate;

    public Optional<Rule> findByRuleId(int ruleId) {
        return this.ruleHibernate.findById(ruleId);
    }

    public List<Rule> findAllRule() {
        return this.ruleHibernate.findAll();
    }
}
