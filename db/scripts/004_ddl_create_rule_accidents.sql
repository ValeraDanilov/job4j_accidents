create table if not exists rule_accidents
(
    id serial primary key,
    accident_id int not null references accidents(id),
    rule_id int not null references rules(id)
);
