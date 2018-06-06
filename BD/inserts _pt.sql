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

--USERS
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('pedro', 'pedro@example.com', 21, 'Pedro Oliveira', '123');
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('alice', 'alice@example.com', 21, 'Alice Oliveira', '123');
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('xavier', 'xavier@example.com', 21, 'Xavier Santos', '123');

--USER-HOUSE
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (1, 'pedro', true);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (1, 'alice', false);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (2, 'pedro', true);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (2, 'xavier', false);

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
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 3, 'Becel', '250', 'Manteiga Vegetal', 1, 'kg', 'Menos 50% de gordura. Sem sal adicionado.', 'Fridge');
	
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
SELECT insert_system_list(1, 'Lista de Compras');
SELECT insert_system_list(2, 'Lista de Compras');

--LIST-PRODUCT
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (1, 3, 4, 'Marlene', 6);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (1, 3, 5, 'Strawberry', 15);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (1, 3, 6, 'Lucato', 4);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (2, 3, 4, 'Marlene', 2);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (2, 3, 5, 'Strawberry', 12);
INSERT INTO public.listproduct(house_id, list_id, product_id, listproduct_brand, listproduct_quantity) 
	VALUES (2, 3, 6, 'Lucato', 6);
	
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
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2017-10-20'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2017-10-22'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, true, '2018-03-16'::timestamp, 1);
--House1-yogurt
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 1, true, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 1, true, '2018-03-16'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 1, false, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 1, false, '2018-03-17'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 1, true, '2018-03-17'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 1, false, '2018-03-20'::timestamp, 1);
--House2-yogurt
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 1, true, '2018-06-01'::timestamp, 6);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 1, false, '2018-06-01'::timestamp, 6);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 1, false, '2018-06-02'::timestamp, 3);
--House2-butter
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, true, '2018-04-14'::timestamp, 1);
	
--STOCKITEM-STORAGE
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (1, 'P1-Mimosa-UHT Magro-1l', 1, 4);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (1, 'P2-Agros-Iogurte Natural-250ml', 1, 6);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (2, 'P2-Agros-Iogurte Natural-250ml', 1, 15);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (2, 'P3-Becel-Manteiga Vegetal-1kg', 1, 1);


