package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeRepository typeHibernate;

    public Optional<AccidentType> findByAccidentTypeId(int accidentTypeId) {
        return this.typeHibernate.findById(accidentTypeId);
    }

    public List<AccidentType> findAllAccidentsTyp() {
        return this.typeHibernate.findAll();
    }
}
