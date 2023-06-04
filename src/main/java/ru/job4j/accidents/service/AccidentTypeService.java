package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeHibernate;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeHibernate typeHibernate;

    public AccidentType findByAccidentTypeId(int accidentTypeId) {
        return this.typeHibernate.findByAccidentTypeId(accidentTypeId);
    }

    public List<AccidentType> findAllAccidentsTyp() {
        return this.typeHibernate.findAllAccidentsType();
    }
}
