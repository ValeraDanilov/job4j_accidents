package ru.job4j.ru.service;

import org.springframework.stereotype.Service;
import ru.job4j.ru.model.Accident;
import ru.job4j.ru.model.AccidentType;
import ru.job4j.ru.repository.AccidentRepository;

import java.util.List;

@Service
public class AccidentService {

    private final AccidentRepository repository;

    public AccidentService(AccidentRepository repository) {
        this.repository = repository;
    }

    public void create(Accident accident) {
        this.repository.create(accident);
    }

    public Accident findById(int id) {
        return this.repository.findById(id);
    }

    public AccidentType findByIdType(int id) {
        return this.repository.findByIdType(id);
    }

    public List<AccidentType> findAllType() {
        return this.repository.findAllType();
    }

    public List<Accident> findAll() {
        return this.repository.findAll();
    }

    public void update(Accident accident) {
        this.repository.update(accident);
    }

    public boolean delete(Accident accident) {
        return this.repository.delete(accident);
    }
}
