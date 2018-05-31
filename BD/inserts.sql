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

INSERT INTO public.category(category_name) VALUES ('Categoria1');
INSERT INTO public.category(category_name) VALUES ('Categoria2');

INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
VALUES (1, 'Product 1', true, 1, 'day');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
VALUES (1, 'Product 2', true, 1, 'day');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
VALUES (1, 'Product 3', true, 1, 'year');

INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
VALUES (2, 'Product 1', true, 1, 'week');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
VALUES (2, 'Product 2', true, 1, 'day');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
VALUES (2, 'Product 3', true, 1, 'month');

SELECT insert_user_list(1, 'Lista 1', 'ze', false);
SELECT insert_user_list(1, 'Lista 2', 'ze', true);
SELECT insert_user_list(2, 'Lista 1', 'ze', true);
SELECT insert_user_list(2, 'Lista 2', 'joaquim', true);

SELECT insert_system_list(1, 'Lista sys');
SELECT insert_system_list(2, 'Lista sys');





