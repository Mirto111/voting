DELETE FROM dishes;
DELETE FROM vote;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 1;


INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'user'),
('Admin', 'admin@gmail.com', 'admin'),
  ('User1', 'user1@yandex.ru', 'user1');


INSERT INTO restaurants(name)
    VALUES ('Три кабана'),
      ('Кфс'),
      ('Иль Патио'),
      ('У фонтана');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_ADMIN', 2),
  ('ROLE_USER', 2),
  ('ROLE_USER', 3);

INSERT INTO dishes( description,price ,rest_id)
VALUES('Рыбный суп',10.05,4),
  ('Каша гречневая',11.12,4),
  ('Салат "Цезарь" ',5.7,5),
  ('Омлет',8.16,5),
  ('Чай зеленый',8.16,4),
  ('Чай черный',8.16,5),
  ('Салат из свежих овощей',8.16,6),
  ('Филе судака',8.16,6),
  ('Сок ананасовый',8.16,6),
  ('Cуп с креветками',8.16,7),
  ('Пицца пеперони',8.16,7),
  ('Тирамису',8.16,7),
  ('Какао',8.16,7);

INSERT INTO vote (rest_name, count_vote) VALUES
  ('Три кабана',2),('Иль Патио',5);