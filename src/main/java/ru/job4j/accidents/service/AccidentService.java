package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentRepository accident;


    public void create(Accident accident) {
        this.accident.save(accident);
    }

    public Optional<Accident> findByAccidentId(int accidentId) {
        return this.accident.findById(accidentId);
    }
    public List<Accident> findAll() {
        return this.accident.findAll();
    }

    public void update(Accident accident) {
        this.accident.save(accident);
    }

    public void delete(Accident accident) {
        this.accident.delete(accident);
    }
}
