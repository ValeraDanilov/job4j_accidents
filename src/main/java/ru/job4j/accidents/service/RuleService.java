package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleHibernate;

import java.util.List;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleHibernate ruleHibernate;

    public Rule findByRuleId(int ruleId) {
        return this.ruleHibernate.findByRuleId(ruleId);
    }

    public List<Rule> findAllRule() {
        return this.ruleHibernate.findAllRule();
    }
}
