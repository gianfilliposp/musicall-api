INSERT INTO instrument (name) VALUES
('Guitarra'),
('Baixo'),
('Bateria'),
('Teclado'),
('Piano'),
('Violino'),
('Trompete'),
('Saxofone'),
('Clarinete'),
('Flauta'),
('Violão'),
('Cavaquinho'),
('Pandeiro'),
('Surdo'),
('Tamborim');

INSERT INTO user (birth_date, confirmation_token, cpf, email, is_confirmed, is_password_reset_requested, last_name, name, number_of_events, password, password_reset_token, telephone, type)
VALUES ('2000-01-01', 'c111cf08-a6e8-4611-b624-e1d4c4a45978', '12345678901', 'john.doe@example.com', true, false, 'Doe', 'John', 0, '$2a$10$H9BHHHpnGo2lrMCeGGcRWuZC5Pt9pL9K6dGz3yNCorqw0LoM1l.fS', '', '99999999999', 'ORG'),
       ('2000-01-01', 'c111cf08-a6e8-4611-b624-e1d4c4a45978', '12345678903', 'john2.doe@example.com', true, false, 'Doe', 'John', 0, '$2a$10$IgJaWyQfnW1Vu7huiZ5Ht.hrWb0xKhGWIrTPciOzriBdxslN.4qXK', '', '99919999999', 'MSC');
