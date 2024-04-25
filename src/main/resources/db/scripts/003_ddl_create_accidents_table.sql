CREATE TABLE if not exists accidents
(
    id   serial primary key,
    name text,
    description text,
    type_id int not null references accidentsType(id),
    address text,
    status text
);
