DELETE FROM dishes;
DELETE FROM vote;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;


INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'user'),
('Admin', 'admin@gmail.com', 'admin'),
  ('User1', 'user1@yandex.ru', 'user1');


INSERT INTO restaurants(name)
    VALUES ('Три кабана'),
      ('Кфс'),
      ('Иль Патио');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_ADMIN', 2),
  ('ROLE_USER', 2),
  ('ROLE_USER', 3);

INSERT INTO dishes( description,price ,rest_id)
VALUES('Суп',10.05,4),
  ('Каша',11.12,5),
  ('Салат',5.7,6),
  ('Омлет',8.16,6);

INSERT INTO vote (rest_name, count) VALUES
  ('Три кабана',2),('Иль Патио',5);