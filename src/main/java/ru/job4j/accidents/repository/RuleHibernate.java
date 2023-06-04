package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.util.SessionWrapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class RuleHibernate {

    private final SessionFactory sf;

    public Rule findByRuleId(int ruleId) {
        return (Rule) SessionWrapper.wrap(
                session -> session
                        .createQuery("from Rule where id = :Id")
                        .setParameter("Id", ruleId)
                        .getSingleResult(), this.sf);
    }

    public List<Rule> findAllRule() {
        return SessionWrapper.wrap(
                session -> session
                        .createQuery("from Rule ")
                        .list(), this.sf);
    }
}
