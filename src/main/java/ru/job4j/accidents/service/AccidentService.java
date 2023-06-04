package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentHibernate;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentHibernate accident;

    public void create(Accident accident) {
        this.accident.create(accident);
    }

    public Accident findByAccidentId(int accidentId) {
        return this.accident.findByAccidentId(accidentId);
    }

    public List<Accident> findAll() {
        return this.accident.findAll()
                .stream()
                .distinct()
                .sorted(Comparator.comparing(Accident::getId))
                .toList();
    }

    public void update(Accident accident) {
        this.accident.update(accident);
    }

    public void delete(Accident accident) {
        this.accident.delete(accident);
    }
}
