--HOUSE
INSERT INTO public.house(house_name, house_characteristics) VALUES ('House1', '{
  "house_babiesNumber": 0,
  "house_childrenNumber": 2,
  "house_adultsNumber": 2,
  "house_seniorsNumber": 0
}');
INSERT INTO public.house(house_name, house_characteristics) VALUES ('House2', '{
  "house_babiesNumber": 1,
  "house_childrenNumber": 0,
  "house_adultsNumber": 2,
  "house_seniorsNumber": 1
}');

--USERS
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('ze', 'ze@gmail.com', 21, 'ze', '123');
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('maria', 'maria@gmail.com', 21, 'maria', '123');
INSERT INTO public.users(users_username, users_email, users_age, users_name, users_password) VALUES ('joaquim', 'joaquim@gmail.com', 21, 'joaquim', '123');

--USER-HOUSE
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (1, 'ze', true);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (1, 'maria', false);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (2, 'ze', true);
INSERT INTO public.userhouse(house_id, users_username, userhouse_administrator) VALUES (2, 'joaquim', false);

--Allergy
INSERT INTO public.allergy(allergy_allergen) VALUES ('Milk');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Eggs');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Cereals');
INSERT INTO public.allergy(allergy_allergen) VALUES ('Fish');

--STORAGES
INSERT INTO public.storage(house_id, storage_id, storage_name, storage_temperature)
	VALUES (1, 1, 'Fridge', '[1,5]');
INSERT INTO public.storage(house_id, storage_id, storage_name, storage_temperature)
	VALUES (2, 1, 'Fridge', '[1,5]');

--DATE
INSERT INTO public.date(date_date) VALUES (to_date('2019-06-01', 'YYYY-MM-DD'));
INSERT INTO public.date(date_date) VALUES (to_date('2018-09-01', 'YYYY-MM-DD'));
INSERT INTO public.date(date_date) VALUES (to_date('2019-01-15', 'YYYY-MM-DD'));
INSERT INTO public.date(date_date) VALUES (to_date('2018-05-01', 'YYYY-MM-DD'));

--CATEGORY
INSERT INTO public.category(category_name) VALUES ('Dairy');
INSERT INTO public.category(category_name) VALUES ('Fruits');
INSERT INTO public.category(category_name) VALUES ('Grains, beans and legumes');
INSERT INTO public.category(category_name) VALUES ('Meat');
INSERT INTO public.category(category_name) VALUES ('Confections');
INSERT INTO public.category(category_name) VALUES ('Vegetables');
INSERT INTO public.category(category_name) VALUES ('Water');
INSERT INTO public.category(category_name) VALUES ('Alcohol');
INSERT INTO public.category(category_name) VALUES ('Pasta');
INSERT INTO public.category(category_name) VALUES ('Salads');

--PRODUCT
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (1, 'Milk', true, 1, 'day');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (1, 'Yogurt', true, 1, 'day');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (1, 'Butter', true, 1, 'year');

INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (2, 'Apple', true, 1, 'week');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (2, 'strawberry', true, 1, 'week');
INSERT INTO public.product(category_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
	VALUES (2, 'Orange', true, 1, 'month');

--STOCKITEM
INSERT INTO public.stockitem(house_id, stockitem_sku, product_id, stockitem_brand, stockitem_segment, stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage)
	VALUES (1, 'P1-Mimosa-Ligth-1l', 1, 'Mimosa', '1', 'Ligth', 4, 'l', 'Very good milk', 'Fridge');
INSERT INTO public.stockitem(house_id, stockitem_sku, product_id, stockitem_brand, stockitem_segment, stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage)
	VALUES (1, 'P2-Agros-Ligth-250ml', 2, 'Agros', '250', 'Ligth', 6, 'ml', 'yogurt ligth', 'Fridge');
INSERT INTO public.stockitem(house_id, stockitem_sku, product_id, stockitem_brand, stockitem_segment, stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage)
	VALUES (2, 'P2-Agros-Ligth-250ml', 2, 'Agros', '250', 'Ligth', 15, 'ml', 'yogurt ligth', 'Fridge');
INSERT INTO public.stockitem(house_id, stockitem_sku, product_id, stockitem_brand, stockitem_segment, stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage)
	VALUES (2, 'P3-Becel-Vegetal-250ml', 3, 'Becel', '250', 'Vegetal', 1, 'g', 'Vegetal Butter', 'Fridge');
	
--EXPIRATION-DATE
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (1, 'P1-Mimosa-Ligth-1l', to_date('2018-05-01', 'YYYY-MM-DD'), 2);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (1, 'P1-Mimosa-Ligth-1l', to_date('2018-09-01', 'YYYY-MM-DD'), 2);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (1, 'P2-Agros-Ligth-250ml', to_date('2019-01-15', 'YYYY-MM-DD'), 4);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (1, 'P2-Agros-Ligth-250ml', to_date('2019-01-15', 'YYYY-MM-DD'), 2);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (2, 'P2-Agros-Ligth-250ml', to_date('2018-09-01', 'YYYY-MM-DD'), 10);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (2, 'P2-Agros-Ligth-250ml', to_date('2018-05-01', 'YYYY-MM-DD'), 5);
INSERT INTO public.expirationdate(house_id, stockitem_sku, date_date, date_quantity) VALUES (2, 'P3-Becel-Vegetal-250ml', to_date('2019-06-01', 'YYYY-MM-DD'), 2);

--HOUSE-ALLERGY
INSERT INTO public.houseallergy(house_id, allergy_allergen, houseallergy_alergicsnum) VALUES (1, 'Milk', 1);
INSERT INTO public.houseallergy(house_id, allergy_allergen, houseallergy_alergicsnum) VALUES (1, 'Cereals', 1);
INSERT INTO public.houseallergy(house_id, allergy_allergen, houseallergy_alergicsnum) VALUES (2, 'Fish', 2);

--LISTS
SELECT insert_user_list(1, 'Party', 'ze', false);
SELECT insert_user_list(1, 'Dark List', 'ze', true);
SELECT insert_user_list(2, 'Pool Day', 'ze', true);
SELECT insert_user_list(2, 'New Products to Try', 'joaquim', true);
SELECT insert_system_list(1, 'Groceries List');
SELECT insert_system_list(2, 'Groceries List');

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
	VALUES (1, 'P1-Mimosa-Ligth-1l', 'Milk');
INSERT INTO public.stockitemallergy(house_id, stockitem_sku, allergy_allergen)
	VALUES (1, 'P2-Agros-Ligth-250ml', 'Milk');
INSERT INTO public.stockitemallergy(house_id, stockitem_sku, allergy_allergen)
	VALUES (2, 'P2-Agros-Ligth-250ml', 'Milk');
INSERT INTO public.stockitemallergy(house_id, stockitem_sku, allergy_allergen)
	VALUES (2, 'P3-Becel-Vegetal-250ml', 'Milk');

--STOCKITEM-MOVEMENT
--House1-Milk
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P1-Mimosa-Ligth-1l', 1, true, '2017-10-20'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P1-Mimosa-Ligth-1l', 1, true, '2017-10-20'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P1-Mimosa-Ligth-1l', 1, true, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P1-Mimosa-Ligth-1l', 1, true, '2018-03-14'::timestamp, 1);
--House1-yogurt
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Ligth-250ml', 1, true, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Ligth-250ml', 1, true, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Ligth-250ml', 1, true, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Ligth-250ml', 1, true, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Ligth-250ml', 1, true, '2018-03-14'::timestamp, 1);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (1, 'P2-Agros-Ligth-250ml', 1, true, '2018-03-14'::timestamp, 1);
--House2-yogurt
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (2, 'P2-Agros-Ligth-250ml', 1, true, '2018-06-01'::timestamp, 6);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (2, 'P2-Agros-Ligth-250ml', 1, true, '2018-06-01'::timestamp, 6);
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (2, 'P2-Agros-Ligth-250ml', 1, true, '2018-06-01'::timestamp, 3);
--House2-butter
INSERT INTO public.stockitemmovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity)
	VALUES (2, 'P3-Becel-Vegetal-250ml', 1, true, '2018-04-14'::timestamp, 1);
	
--STOCKITEM-STORAGE
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (1, 'P1-Mimosa-Ligth-1l', 1, 4);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (1, 'P2-Agros-Ligth-250ml', 1, 6);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (2, 'P2-Agros-Ligth-250ml', 1, 15);
INSERT INTO public.stockitemstorage(house_id, stockitem_sku, storage_id, stockitemstorage_quantity)
	VALUES (2, 'P3-Becel-Vegetal-250ml', 1, 1);


