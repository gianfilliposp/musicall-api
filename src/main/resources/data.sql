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
('Voz'),
('Tamborim');

INSERT INTO user (birth_date, confirmation_token, cpf, email, is_confirmed, is_password_reset_requested, name, number_of_events, password, password_reset_token, telephone, type)
VALUES ('2000-01-01', 'c111cf08-a6e8-4611-b624-e1d4c4a45978', '12345678901', 'john.doe@example.com', true, false, 'John', 0, '$2a$10$H9BHHHpnGo2lrMCeGGcRWuZC5Pt9pL9K6dGz3yNCorqw0LoM1l.fS', '', '99999999999', 'ORG'),
       ('2000-01-01', 'c111cf08-a6e8-4611-b624-e1d4c4a45978', '12345678903', 'john2.doe@example.com', true, false, 'John', 0, '$2a$10$IgJaWyQfnW1Vu7huiZ5Ht.hrWb0xKhGWIrTPciOzriBdxslN.4qXK', '', '99919999999', 'MSC');

INSERT INTO musician (user_id, cep, description, image_url)
VALUES (2, '03366-010', 'Tá ligado né pai', 'https://images-ext-1.discordapp.net/external/xoznNY--RFbvLjzTXXVBCTw9bJuwe7I77QYhdP5AoSA/%3Fauto%3Dcompress%26cs%3Dtinysrgb%26w%3D1260%26h%3D750%26dpr%3D1/https/images.pexels.com/photos/977971/pexels-photo-977971.jpeg?width=985&height=656');

INSERT INTO event (user_id, name, about_event, cep, number, complement, event_date, duration_hours, start_hour, finalized, image_url)
VALUES
  (1, 'My Event - AL', 'sei lá', '01414-001', 2, 'My Complement', '2024-01-02', 2, '00:00:00', false, 'https://images.pexels.com/photos/2592179/pexels-photo-2592179.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
  (1, 'My Event - AL', 'sei lá', '57000-000', 2, 'My Complement', '2024-01-02', 2, '00:00:00', false, 'https://images.pexels.com/photos/2774556/pexels-photo-2774556.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - AM', 'sei lá', '69000-000', 2, 'My Complement', '2024-01-04', 2, '00:00:00', false, 'https://images.pexels.com/photos/587741/pexels-photo-587741.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - CE', 'sei lá', '60000-000', 2, 'My Complement', '2024-01-06', 2, '00:00:00', false, 'https://images.pexels.com/photos/1190297/pexels-photo-1190297.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - GO', 'sei lá', '74000-000', 2, 'My Complement', '2024-01-09', 2, '00:00:00', false, 'https://images.pexels.com/photos/1190297/pexels-photo-1190297.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - MA', 'sei lá', '65000-000', 2, 'My Complement', '2024-01-10', 2, '00:00:00', false, 'https://images.pexels.com/photos/919734/pexels-photo-919734.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - MT', 'sei lá', '78000-000', 2, 'My Complement', '2024-01-11', 2, '00:00:00', false, 'https://images.pexels.com/photos/919734/pexels-photo-919734.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - PB', 'sei lá', '58000-000', 2, 'My Complement', '2024-01-15', 2, '00:00:00', false, 'https://images.pexels.com/photos/2747446/pexels-photo-2747446.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - PE', 'sei lá', '50000-000', 2, 'My Complement', '2024-01-17', 2, '00:00:00', false, 'https://images.pexels.com/photos/313707/pexels-photo-313707.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - PI', 'sei lá', '64000-000', 2, 'My Complement', '2024-01-18', 2, '00:00:00', false, 'https://images.pexels.com/photos/265947/pexels-photo-265947.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - RJ', 'sei lá', '20000-000', 2, 'My Complement', '2024-01-19', 2, '00:00:00', false, 'https://images.pexels.com/photos/265947/pexels-photo-265947.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - RN', 'sei lá', '59000-000', 2, 'My Complement', '2024-01-20', 2, '00:00:00', false, 'https://images.pexels.com/photos/1652353/pexels-photo-1652353.jpeg?auto=compress&cs=tinysrgb&w=1600'),
  (1, 'My Event - RS', 'sei lá', '90000-000', 2, 'My Complement', '2024-01-21', 2, '00:00:00', false, 'https://images.pexels.com/photos/1652353/pexels-photo-1652353.jpeg?auto=compress&cs=tinysrgb&w=1600');

INSERT INTO event_job (event_id, instrument_id, musician_id, payment)
VALUES
  (1, 2, NULL, 10.0),
  (1, 3, NULL, 10.0),
  (1, 4, NULL, 10.0),
  (1, 5, NULL, 10.0),
  (1, 6, NULL, 10.0),
  (2, 2, NULL, 10.0),
  (2, 3, NULL, 10.0),
  (2, 4, NULL, 10.0),
  (2, 5, NULL, 10.0),
  (2, 6, NULL, 10.0),
  (3, 2, NULL, 10.0),
  (3, 3, NULL, 10.0),
  (3, 4, NULL, 10.0),
  (3, 5, NULL, 10.0),
  (3, 6, NULL, 10.0),
  (11, 2, NULL, 10.0),
  (11, 3, NULL, 10.0),
  (11, 3, NULL, 10.0);

INSERT INTO musician_instrument (musician_id, instrument_id) VALUES
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6);

INSERT INTO job_request (event_job_id, musician_id, organizer_confirmed, musician_confirmed) VALUES
(1, 1, 0, 1),
(2, 1, 0, 1);

INSERT INTO notification (job_request_id, notification_type, user_id) VALUES
(1, 1, 1),
(2, 1,1);
