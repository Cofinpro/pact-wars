create schema if not exists persons authorization pactwars;

create table if not exists persons.persons
(
    id bigint not null
        constraint persons_pk
            primary key,
    name varchar(255),
    image varchar(255),
    on_planet_id bigint
);

alter table persons.persons owner to pactwars;

DELETE FROM persons.persons;

INSERT INTO persons.persons
(id, name, image, on_planet_id)
VALUES
    (
        1,
        'Darts Evader',
        'assets/person/person_1.png',
        2
    ),
    (
        2,
        'Duke Guystalker',
        'assets/person/person_2.png',
        5
    ),
    (
        3,
        'Master Yodler',
        'assets/person/person_3.png',
        3
    ),
    (
        4,
        'Toby-Rhuan Shenobi',
        'assets/person/person_4.png',
        5
    ),
    (
        5,
        'Prinzessin Neia Morgana',
        'assets/person/person_5.png',
        1
    ),
    (
        6,
        'Hans Sorrow',
        'assets/person/person_6.png',
        5
    ),
    (
        7,
        'Chewbaraca',
        'assets/person/person_7.png',
        5
    ),
    (
        8,
        'Grand Muffin Parkins',
        'assets/person/person_8.png',
        1
    );
