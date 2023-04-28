package ru.job4j.ru.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.ru.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private int index = 3;

    {
        Accident firstAccident = new Accident(1, "Name", "Text", "Address");
        Accident secondAccident = new Accident(2, "Name1", "Text", "Address1");
        Accident thirdAccident = new Accident(3, "Name2", "Text", "Address2");
        this.accidents.put(firstAccident.getId(), firstAccident);
        this.accidents.put(secondAccident.getId(), secondAccident);
        this.accidents.put(thirdAccident.getId(), thirdAccident);
    }

    public void create(Accident accident) {
        ++index;
        accident.setId(index);
        this.accidents.put(accident.getId(), accident);
    }

    public Accident findById(int id) {
        return this.accidents.get(id);
    }

    public List<Accident> findAll() {
        List<Accident> accidentsList = new ArrayList<>();
        this.accidents.forEach((k, v) -> accidentsList.add(v));
        return accidentsList;
    }

    public boolean update(Accident accident) {
        Accident oldAccident = this.accidents.get(accident.getId());
        return this.accidents.replace(accident.getId(), oldAccident, accident);
    }

    public boolean delete(Accident accident) {
        return this.accidents.remove(accident.getId(), accident);
    }
}
