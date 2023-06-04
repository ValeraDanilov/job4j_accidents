package ru.job4j.accidents.repository;


import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.util.SessionWrapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate {

    private final SessionFactory sf;

    public AccidentType findByAccidentTypeId(int accidentTypeId) {
        return (AccidentType) SessionWrapper.wrap(
                session -> session
                        .createQuery("from AccidentType where id = :Id")
                        .setParameter("Id", accidentTypeId)
                        .getSingleResult(), this.sf);
    }

    public List<AccidentType> findAllAccidentsType() {
        return SessionWrapper.wrap(
                session -> session
                        .createQuery("from AccidentType ")
                        .list(), this.sf);
    }
}
