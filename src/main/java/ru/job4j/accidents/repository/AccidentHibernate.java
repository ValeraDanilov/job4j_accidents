package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.util.SessionWrapper;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class AccidentHibernate {

    private final SessionFactory sf;
    private static final String SELECT_ALL_QUERY = "from Accident  accident join fetch accident.type join fetch accident.rules order by accident.id";
    private static final String SELECT_BY_ID_QUERY = "from Accident accident join fetch accident.type join fetch accident.rules"
            + " where accident.id = :Id";

    public void create(Accident accident) {
        SessionWrapper.wrap(session -> session.save(accident), this.sf);
    }

    public Accident findByAccidentId(int accidentId) {
        return (Accident) SessionWrapper.wrap(
                session -> session
                        .createQuery(SELECT_BY_ID_QUERY)
                        .setParameter("Id", accidentId)
                        .getSingleResult(), this.sf);
    }

    public List<Accident> findAll() {
        return SessionWrapper.wrap(session -> session
                .createQuery(SELECT_ALL_QUERY)
                .stream().distinct().toList(), this.sf);

    }

    public void update(Accident accident) {
        var findAccident = findByAccidentId(accident.getId());
        if (findAccident == null) {
            throw new NoSuchElementException("Invalid Accident id");
        }
        SessionWrapper.wrap(session -> {
            session.saveOrUpdate(accident);
            return null;
        }, this.sf);
    }

    public void delete(Accident accident) {
        var findAccident = findByAccidentId(accident.getId());
        if (findAccident == null) {
            throw new NoSuchElementException("Invalid Accident id");
        }
        SessionWrapper.wrap(session -> {
            session.delete(accident);
            return null;
        }, this.sf);
    }
}
