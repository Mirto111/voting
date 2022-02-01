INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '$2a$10$lvB2KsXBJCRHkjzc7puy9.LPM3GxZuXspshPgHtAIez4a/PjkjOlC'),
('Admin', 'admin@gmail.com', '$2a$10$TEM2ZgHZGTPQMjCIzy6ciubuo5.1iGd5mfFxYFmNIqll4q8F1Jn.y'),
  ('User1', 'user1@yandex.ru', '{noop}user1');


INSERT INTO restaurants(name)
    VALUES ('Три кабана'),
      ('Kfc'),
      ('Ilpatio'),
      ('У фонтана');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_ADMIN', 2),
  ('ROLE_USER', 2),
  ('ROLE_USER', 3);

INSERT INTO dishes( description,price ,rest_id)
VALUES('Рыбный суп',100.05,1),
  ('Каша гречневая',111.12,1),
  ('Салат "Цезарь" ',55.7,2),
  ('Омлет',119.14,2),
  ('Чай зеленый',23.16,2),
  ('Чай черный',18.16,2),
  ('Салат из свежих овощей',48.16,3),
  ('Филе судака',306.15,3),
  ('Сок ананасовый',30.40,3),
  ('Cуп с креветками',160.54,4),
  ('Пицца пеперони',180.25,4),
  ('Тирамису',90.16,4),
  ('Какао',28.10,4);

INSERT INTO voting_result (vote_date,rest_name, count_vote) VALUES
  ('2018-05-20','Три кабана',2),('2018-05-20','Иль Патио',5);