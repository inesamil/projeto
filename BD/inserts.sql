INSERT INTO public.house(house_name, house_characteristics) VALUES ('House1', '{}');
INSERT INTO public.house(house_name, house_characteristics) VALUES ('House2', '{
  "house_babiesNumber": 2,
  "house_childrenNumber": 2,
  "house_adultsNumber": 2,
  "house_seniorsNumber": 2
}');

INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('ze', 'ze@gmail.com', 21, 'ze', '123');
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('maria', 'maria@gmail.com', 21, 'maria', '123');
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('joaquim', 'joaquim@gmail.com', 21, 'joaquim', '123');

INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (1, 'ze', true);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (1, 'maria', false);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (2, 'ze', true);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (2, 'joaquim', false);

