package ru.job4j.ru.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.ru.model.Accident;
import ru.job4j.ru.model.AccidentType;
import ru.job4j.ru.model.Rule;
import ru.job4j.ru.repository.AccidentJdbcTemplate;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentJdbcTemplate accidentsRepository;

    public void create(Accident accident) {
        this.accidentsRepository.create(accident);
    }

    public Accident findByAccidentId(int accidentId) {
        return this.accidentsRepository.findByAccidentId(accidentId);
    }

    public List<Accident> findAll() {
        return this.accidentsRepository.findAll().stream().sorted(Comparator.comparing(Accident::getId)).toList();
    }

    public AccidentType findByTypeId(int typeId) {
        return this.accidentsRepository.findByTypeId(typeId);
    }

    public List<AccidentType> findAllType() {
        return this.accidentsRepository.findAllType();
    }

    public List<Rule> findAllRules() {
        return this.accidentsRepository.findAllRules();
    }

    public Rule findByRuleId(int ruleId) {
        return this.accidentsRepository.findByRuleId(ruleId);
    }

    public void update(Accident accident) {
        this.accidentsRepository.update(accident);
    }
}
