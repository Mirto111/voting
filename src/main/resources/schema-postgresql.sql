DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS voting_result;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

CREATE TABLE restaurants
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR NOT NULL
);


CREATE TABLE dishes
(
    id          INTEGER PRIMARY KEY,
    date_time   DATE    NOT NULL DEFAULT now(),
    description VARCHAR NOT NULL,
    price       NUMERIC NOT NULL,
    rest_id     INTEGER NOT NULL,
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE

);
CREATE INDEX dishes_rest_dish_idx ON dishes (rest_id, date_time);


CREATE TABLE users
(
    id       INTEGER PRIMARY KEY,
    email    VARCHAR NOT NULL UNIQUE,
    name     VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE voting_result
(
    id         INTEGER PRIMARY KEY,
    vote_date  DATE    NOT NULL DEFAULT now(),
    rest_name  VARCHAR NOT NULL,
    count_vote INTEGER NOT NULL
);