package ru.job4j.ru.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.ru.model.Accident;
import ru.job4j.ru.model.AccidentType;
import ru.job4j.ru.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public void create(Accident accident) {
        jdbc.update("insert into accidents (name, description, type_id, address) values (?, ?, ?, ?)",
                accident.getName(),
                accident.getDescription(),
                accident.getType().getId(),
                accident.getAddress());
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into rule_accidents (accident_id, rule_id) values (?, ?)",
                    findByAccident(accident).getId(),
                    rule.getId()
            );
        }
    }

    public Accident findByAccidentId(int accidentId) {
        String sql = "SELECT * FROM accidents WHERE ID = ?";
        return this.jdbc.queryForObject(
                sql,
                (rs, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(accidentId);
                    accident.setName(rs.getString("name"));
                    accident.setDescription(rs.getString("description"));
                    accident.setType(findByTypeId(rs.getInt("type_id")));
                    accident.setRules(new HashSet<>(findByRule(accidentId)));
                    accident.setAddress(rs.getString("address"));
                    return accident;
                }, accidentId);
    }

    private List<Rule> findByRule(int accidentId) {
        String sql = "SELECT * FROM rule_accidents WHERE accident_id = ?";
        return this.jdbc.query(sql,
                (rs, row) -> findByRuleId(rs.getInt("rule_id")), accidentId);
    }

    private Accident findByAccident(Accident acc) {
        String sql = "SELECT * FROM accidents WHERE name = ? and description = ? and type_id = ? and address = ?";
        return this.jdbc.queryForObject(
                sql,
                (rs, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    return accident;
                }, acc.getName(), acc.getDescription(), acc.getType().getId(), acc.getAddress());
    }

    public AccidentType findByTypeId(int typeId) {
        String sql = "SELECT * FROM accidentsType WHERE ID = ?";
        return this.jdbc.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    AccidentType type = new AccidentType();
                    type.setName(resultSet.getString("name"));
                    type.setId(typeId);
                    return type;
                },
                typeId);
    }

    public Rule findByRuleId(int typeId) {
        String sql = "SELECT * FROM rule WHERE ID = ?";
        return this.jdbc.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    Rule rule = new Rule();
                    rule.setName(resultSet.getString("name"));
                    rule.setId(typeId);
                    return rule;
                },
                typeId);
    }

    public List<AccidentType> findAllType() {
        String sql = "select id, name from accidentsType";
        return this.jdbc.query(sql,
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

    public List<Accident> getAllAccidents() {
        String sql = "select * from accidents as ac "
                + "left join rule_accidents ra ON ra.accident_id = ac.id "
                + "left join rule r ON r.id = ra.rule_id";
        return this.jdbc.query(sql,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setDescription(rs.getString("description"));
                    accident.setType(findByTypeId(rs.getInt("type_id")));
                    accident.setRules(Set.of(findByRuleId(rs.getInt("rule_id"))));
                    accident.setAddress(rs.getString("address"));
                    return accident;
                });
    }

    public List<Accident> findAll() {
        Set<Accident> accidentsList = new HashSet<>();
        List<Accident> accidents = getAllAccidents();
        for (Accident ac : accidents) {
            Set<Rule> rule = new HashSet<>();
            for (Accident acc : accidents) {
                if (ac.getId() == acc.getId()) {
                    rule.add(acc.getRules().stream().toList().get(0));
                }
            }
            ac.setRules(rule);
            accidentsList.add(ac);
        }
        return accidentsList.stream().toList();
    }

    public List<Rule> findAllRules() {
        String sql = "select id, name from rule";
        return this.jdbc.query(sql,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }
        );
    }

    public void update(Accident accident) {
        String sql = "update accidents set name = ?, description = ?, type_id = ?, address = ? where id = ?";
        String ruleSql = "insert into rule_accidents (accident_id, rule_id) values (?, ?)";
        String deleteSql = "delete from rule_accidents where accident_id = ?";
        this.jdbc.update(sql, accident.getName(), accident.getDescription(), accident.getType().getId(), accident.getAddress(), accident.getId());
        this.jdbc.update(deleteSql, (long) accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbc.update(ruleSql,
                    findByAccident(accident).getId(),
                    rule.getId()
            );
        }
    }
}
