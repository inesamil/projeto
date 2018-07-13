--HOUSE
INSERT INTO public.house(house_name, house_characteristics) VALUES ('Oliveira', '{
  "house_babiesNumber": 0,
  "house_childrenNumber": 2,
  "house_adultsNumber": 2,
  "house_seniorsNumber": 0
}');
INSERT INTO public.house(house_name, house_characteristics) VALUES ('Santos', '{
  "house_babiesNumber": 1,
  "house_childrenNumber": 0,
  "house_adultsNumber": 2,
  "house_seniorsNumber": 1
}');

--ROLE
INSERT INTO public.role(role_name) VALUES ('ROLE_ADMIN');
INSERT INTO public.role(role_name) VALUES ('ROLE_USER');

--USERS
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('pedro', 'pedro@example.com', 21, 'Pedro Oliveira', '$2a$04$zPaKKoIxTHUcNCUXk0/.VeqQ.lrYmd1jDF.5fVdqhoDlLwuzClpva');
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('alice', 'alice@example.com', 21, 'Alice Oliveira', '$2a$04$L0KJGuuVT7zCfrX8FQg5XeuZxaCz9MMBlO67iwwPiEyD2J3J4cOKG');
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('xavier', 'xavier@example.com', 21, 'Xavier Santos', '$2a$04$/bbnfYLs0szIUgHSsF.ixumynyCAUiCWLsablvd0E7Kpn4G2eEjUa');

--USER-ROLE
INSERT INTO public.usersrole(users_id, role_id) VALUES (1, 2);
INSERT INTO public.usersrole(users_id, role_id) VALUES (2, 2);
INSERT INTO public.usersrole(users_id, role_id) VALUES (3, 2);

--USER-HOUSE
INSERT INTO public.userhouse(house_id, users_id, userhouse_administrator) VALUES (1, 1, true);
INSERT INTO public.userhouse(house_id, users_id, userhouse_administrator) VALUES (1, 2, false);
INSERT INTO public.userhouse(house_id, users_id, userhouse_administrator) VALUES (2, 1, true);
INSERT INTO public.userhouse(house_id, users_id, userhouse_administrator) VALUES (2, 3, false);

--Allergy
INSERT INTO public.allergy(allergy_allergen) VALUES ('Leite');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Ovo');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Trigo');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Amendoim');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Marisco');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Peixe');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Soja');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Glúten');

--STORAGES
INSERT INTO public.storage(house_id, storage_id, storage_name, storage_temperature)
	VALUES (1, 1, 'Frigorífico', '[1,5]');
INSERT INTO public.storage(house_id, storage_id, storage_name, storage_temperature)
	VALUES (2, 1, 'Frigorífico', '[1,5]');

--DATE
INSERT INTO public.date(date_date) VALUES (to_date('2019-06-01', 'YYYY-MM-DD'));
INSERT INTO public.date(date_date) VALUES (to_date('2018-09-01', 'YYYY-MM-DD'));
INSERT INTO public.date(date_date) VALUES (to_date('2019-01-15', 'YYYY-MM-DD'));
INSERT INTO public.date(date_date) VALUES (to_date('2018-05-01', 'YYYY-MM-DD'));

--CATEGORY
INSERT INTO public.category(category_name) VALUES ('Laticínios');
INSERT INTO public.category(category_name) VALUES ('Frutas');
INSERT INTO public.category(category_name) VALUES ('Legumes');
INSERT INTO public.category(category_name) VALUES ('Bebidas');
INSERT INTO public.category(category_name) VALUES ('Congelados');
INSERT INTO public.category(category_name) VALUES ('Frescos');
INSERT INTO public.category(category_name) VALUES ('Marisco');
INSERT INTO public.category(category_name) VALUES ('Peixe');
INSERT INTO public.category(category_name) VALUES ('Carne');
INSERT INTO public.category(category_name) VALUES ('Massas');
INSERT INTO public.category(category_name) VALUES ('Snacks');
INSERT INTO public.category(category_name) VALUES ('Enlatados');
INSERT INTO public.category(category_name) VALUES ('Condimentos e Molhos');
INSERT INTO public.category(category_name) VALUES ('Ervas e Especiarias');


--PRODUCT
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (1, 'Leite', true, 1, 'day');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (1, 'Iogurte', true, 1, 'day');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (1, 'Manteiga', true, 1, 'year');

INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (2, 'Maçã', true, 1, 'week');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (2, 'Morango', true, 1, 'week');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (2, 'Laranja', true, 1, 'month');

--STOCKITEM
INSERT INTO public.stockitem(house_id, stockitem_sku, product_id, stockitem_brand, stockitem_segment, stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, 'Mimosa', '1', 'UHT Magro', 4, 'l', 'Leite UHT Magro Bem Essencial', 'Não necessita de frigorífico antes de abrir. ');
INSERT INTO public.stockitem(house_id, stockitem_sku, product_id, stockitem_brand, stockitem_segment, stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 2, 'Agros', '250', 'Iogurte Natural', 6, 'ml', 'Sem corantes nem conservantes.', 'Conservar entre 0ºC e 6ºC.');
INSERT INTO public.stockitem(house_id, stockitem_sku, product_id, stockitem_brand, stockitem_segment, stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 2, 'Agros', '250', 'Iogurte Natural', 15, 'ml', 'Sem corantes nem conservantes.', 'Conservar entre 0ºC e 6ºC.');
INSERT INTO public.stockitem(house_id, stockitem_sku, product_id, stockitem_brand, stockitem_segment, stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 3, 'Becel', '1', 'Manteiga Vegetal', 1, 'kg', 'Menos 50% de gordura. Sem sal adicionado.', 'Fridge');
	
--EXPIRATION-DATE
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (1, 'P1-Mimosa-UHT Magro-1l', to_date('2018-05-01', 'YYYY-MM-DD'), 2);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (1, 'P1-Mimosa-UHT Magro-1l', to_date('2018-09-01', 'YYYY-MM-DD'), 2);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (1, 'P2-Agros-Iogurte Natural-250ml', to_date('2019-01-15', 'YYYY-MM-DD'), 4);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (1, 'P2-Agros-Iogurte Natural-250ml', to_date('2019-06-01', 'YYYY-MM-DD'), 2);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (2, 'P2-Agros-Iogurte Natural-250ml', to_date('2018-09-01', 'YYYY-MM-DD'), 10);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (2, 'P2-Agros-Iogurte Natural-250ml', to_date('2018-05-01', 'YYYY-MM-DD'), 5);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', to_date('2019-06-01', 'YYYY-MM-DD'), 2);

--HOUSE-ALLERGY
INSERT INTO public.houseallergy(house_id, allergy_allergen, houseallergy_allergicsnum) VALUES (1, 'Leite', 1);
INSERT INTO public.houseallergy(house_id, allergy_allergen, houseallergy_allergicsnum) VALUES (1, 'Trigo', 1);
INSERT INTO public.houseallergy(house_id, allergy_allergen, houseallergy_allergicsnum) VALUES (2, 'Peixe', 2);

--LISTS
SELECT insert_user_list(1, 'Festa de Aniversário do Xavier', 'pedro', false);
SELECT insert_user_list(1, 'Lista Negra', 'pedro', true);
SELECT insert_user_list(2, 'Dia na Piscina', 'pedro', true);
SELECT insert_user_list(2, 'Produtos para Experimentar', 'xavier', true);
SELECT insert_system_list(1, 1::int2, 'Lista de Compras');
SELECT insert_system_list(2, 1::int2, 'Lista de Compras');

--LIST-PRODUCT
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (1, 1, 4, 'Marlene', 6);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (1, 1, 5, 'Strawberry', 15);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (1, 1, 6, 'Lucato', 4);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (2, 1, 4, 'Marlene', 2);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (2, 1, 5, 'Strawberry', 12);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (2, 1, 6, 'Lucato', 6);
	
--STOCKITEM-ALLERGY
INSERT INTO public.stockitemallergy(house_id, stockitem_sku, allergy_allergen)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 'Leite');
INSERT INTO public.stockitemallergy(house_id, stockitem_sku, allergy_allergen)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 'Leite');
INSERT INTO public.stockitemallergy(house_id, stockitem_sku, allergy_allergen)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 'Leite');
INSERT INTO public.stockitemallergy(house_id, stockitem_sku, allergy_allergen)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 'Leite');

--STOCKITEM-MOVEMENT
--House1-Milk
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-20 09:00:00'::timestamp, 1, 11);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-20 09:02:00'::timestamp, 1, 12);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-20 09:30:00'::timestamp, 1, 11);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-20 09:32:00'::timestamp, 1, 12);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-20 23:32:00'::timestamp, 1, 11);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-21 09:28:00'::timestamp, 1, 10);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-21 09:40:00'::timestamp, 1, 11);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-21 20:28:00'::timestamp, 1, 10);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-21 20:35:00'::timestamp, 1, 11);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-22 09:35:00'::timestamp, 1, 10);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-22 09:35:20'::timestamp, 1, 9);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-22 09:40:00'::timestamp, 1, 10);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-22 10:40:00'::timestamp, 1, 9);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-22 10:45:00'::timestamp, 1, 10);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-22 23:40:00'::timestamp, 1, 9);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-23 09:40:00'::timestamp, 1, 8);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-23 09:42:00'::timestamp, 1, 9);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-24 09:42:00'::timestamp, 1, 8);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-24 09:43:00'::timestamp, 1, 9);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-25 09:40:00'::timestamp, 1, 8);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-25 09:42:00'::timestamp, 1, 9);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-26 09:40:00'::timestamp, 1, 8);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-27 09:40:00'::timestamp, 1, 7);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-27 09:41:00'::timestamp, 1, 8);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-28 09:35:00'::timestamp, 1, 7);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-28 09:36:00'::timestamp, 1, 8);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-29 09:34:00'::timestamp, 1, 7);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-29 09:36:00'::timestamp, 1, 8);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-30 09:31:00'::timestamp, 1, 7);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-06-30 09:31:02'::timestamp, 1, 6);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-06-30 09:35:00'::timestamp, 1, 7);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-01 09:30:00'::timestamp, 1, 6);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-01 09:35:00'::timestamp, 1, 5);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-01 09:37:00'::timestamp, 1, 4);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-02 09:30:00'::timestamp, 1, 3);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-07-02 09:32:00'::timestamp, 1, 4);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-03 09:30:00'::timestamp, 1, 3);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-04 09:30:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-05 09:30:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-07-05 17:30:00'::timestamp, 6, 7);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-06 09:30:00'::timestamp, 1, 6);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-07-06 09:35:00'::timestamp, 1, 7);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-07 09:30:00'::timestamp, 1, 6);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-08 09:30:00'::timestamp, 1, 5);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-07-08 09:35:00'::timestamp, 1, 6);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-09 09:30:00'::timestamp, 1, 5);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-10 09:30:00'::timestamp, 1, 4);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-07-10 09:35:00'::timestamp, 1, 5);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, false, '2018-07-11 09:30:00'::timestamp, 1, 4);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-07-11 09:35:00'::timestamp, 1, 5);

INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-07-12 09:35:00'::timestamp, 1, 6);

--House2-yogurt
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 1, true, '2018-06-01'::timestamp, 6, 6);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 1, false, '2018-06-01'::timestamp, 6, 12);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 1, false, '2018-06-02'::timestamp, 3, 15);
	
--House2-butter	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-20 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-21 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-21 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-22 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-22 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-23 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-23 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-24 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-24 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-25 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-25 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-26 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-26 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-27 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-27 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-28 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-28 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-29 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-29 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-06-30 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-06-30 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-01 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-01 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-02 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-02 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-03 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-03 09:38:00'::timestamp, 1, 2);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-04 09:31:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-05 09:31:00'::timestamp, 1, 0);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-05 09:38:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-06 09:31:00'::timestamp, 1, 0);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-06 09:38:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-07 09:31:00'::timestamp, 1, 0);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-07 09:38:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-08 09:31:00'::timestamp, 1, 0);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-08 09:38:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-09 09:31:00'::timestamp, 1, 0);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-09 09:38:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-10 09:31:00'::timestamp, 1, 0);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-10 09:38:00'::timestamp, 1, 1);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, false, '2018-07-11 09:31:00'::timestamp, 1, 0);
	
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-11 09:38:00'::timestamp, 1, 1);

INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-07-12 09:38:00'::timestamp, 1, 2);
	
--STOCKITEM-STORAGE
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, 4);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 1, 6);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 1, 15);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, 1);
	
--DAILY-QUANTITY
--House1-Milk
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-20', 11);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-21', 11);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-22', 9);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-23', 9);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-24', 9);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-25', 9);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-26', 8);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-27', 8);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-28', 8);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-29', 8);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-06-30', 7);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-01', 4);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-02', 4);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-03', 3);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-04', 2);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-05', 7);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-06', 7);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-07', 6);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-08', 6);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-09', 5);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-10', 5);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-11', 5);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', '2018-07-12', 6);
	
--House2-butter
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-20', 2);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-21', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-22', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-23', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-24', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-25', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-26', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-27', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-28', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-29', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-06-30', 2);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-01', 2);	
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-02', 2);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-03', 2);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-04', 1);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-05', 1);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-06', 1);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-07', 1);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-08', 1);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-09', 1);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-10', 1);
	
INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-11', 1);

INSERT INTO public.dailyquantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', '2018-07-12', 2);