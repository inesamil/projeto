
---TESTS---
SELECT delete_user('ze')
SELECT delete_house(1) 


-------------- TEST get_lists_filtered(houseID bigint, system boolean, username character varying(30), shared boolean) --------------
--drops.sql
--creates.sql
--functions.sql

INSERT INTO public."house" VALUES (1, 'house1', '{"babiesNumber":1}');
INSERT INTO public."house" VALUES (2, 'house2', '{"babiesNumber":2}');
SELECT * FROM public."house"; 

INSERT INTO public."users" VALUES ('ze', 'email', 20, 'ze', '123');
INSERT INTO public."users" VALUES ('maria', 'email.maria', 20, 'maria', '123');
SELECT * FROM public."users";

INSERT INTO public."userhouse" VALUES (1, 'ze', true);
INSERT INTO public."userhouse" VALUES (1, 'maria', false);
SELECT * FROM public."userhouse";

-- Lista de User Partilhada
INSERT INTO public."list" VALUES (1, 1, 'Lista Partilhada do Zé', 'user');
INSERT INTO public."userlist" VALUES (1, 1, 'ze', true);

INSERT INTO public."list" VALUES (1, 2, 'Lista Privada do Zé', 'user');
INSERT INTO public."userlist" VALUES (1, 2, 'ze', false);

-- Lista de User Parilhada
INSERT INTO public."list" VALUES (1, 3, 'Lista Partilhada da Maria', 'user');
INSERT INTO public."userlist" VALUES (1, 3, 'maria', true);

-- Lista de User Não Parilhada
INSERT INTO public."list" VALUES (1, 4, 'Lista Privada da Maria', 'user');
INSERT INTO public."userlist" VALUES (1, 4, 'maria', false);

-- Lista de Sistema
INSERT INTO public."list" VALUES (1, 5, 'Lista de Sistema', 'system');
INSERT INTO public."systemlist" VALUES (1, 5);

INSERT INTO public."list" VALUES (1, 6, 'Lista de Sistema', 'system');
INSERT INTO public."systemlist" VALUES (1, 6);

SELECT * FROM public."list";

SELECT get_lists_filtered (1, true, null, false);	-- ESPERADO: Listas de sistema
SELECT get_lists_filtered (1, true, null, null);	-- ESPERADO: Listas de sistema
SELECT get_lists_filtered (1, true, null, true);	-- ESPERADO: Listas de sistema e Listas partilhadas na casa
SELECT get_lists_filtered (1, true, 'ze', false);	-- ESPERADO: Listas de sistema, Listas do Zé
SELECT get_lists_filtered (1, true, 'ze', true);	-- ESPERADO: Listas de sistema, Listas do Zé e Listas partilhadas na casa
SELECT get_lists_filtered (1, false, 'ze', false);	-- ESPERADO: Listas do Zé
SELECT get_lists_filtered (1, false, 'ze', true);	-- ESPERADO: Listas do Zé e Listas partilhadas na casa
SELECT get_lists_filtered (1, false, 'maria', false);	-- ESPERADO: Listas da Maria
SELECT get_lists_filtered (1, false, null, true);	-- ESPERADO: Listas partilhadas na casa


-------------- TEST get_stock_items_filtered (houseID bigint, productName character varying(35), brand character varying(35), variety character varying(35), segment real, storageID smallint) --------------
--drops.sql
--creates.sql
--functions.sql

INSERT INTO public."house" VALUES (1, 'house1', '{"babiesNumber":1}');
SELECT * FROM public."house"; 

INSERT INTO public."category" VALUES (1, 'Lacticínios'), (2, 'Fruta');
INSERT INTO public."product" VALUES  (1, 1, 'Leite de Vaca', true, 3, 'day'), (2, 1, 'Maçã', true, 2, 'month'), (2, 2, 'Laranja', true, 2, 'month');

INSERT INTO public."storage" VALUES (1, 1, 'Storage 1', numrange(0.0, 5.0));

INSERT INTO public."stockitem" VALUES (1, 'SKU 1', 1, 1, 'Mimosa', 1, 'UHT Magro', 1, 'l', 'Leite Magro da Mimosa é do melhor que há!', 'Conservar em local fresco após abertura.'),
	(1, 'SKU 2', 2, 1, 'Fruta Del Monte', 1, 'Golden', 5, 'units', 'Maçãs Del Monte.', 'Conservar em local seco.');

INSERT INTO public."stockitemstorage" VALUES (1, 'SKU 1', 1, 1), (1, 'SKU 2', 1, 3);

INSERT INTO public."stockitemmovement" VALUES (1, 'SKU 1', 1, true, '2018-04-18 08:02:31', 1), (1, 'SKU 2', 1, true, '2018-04-18 19:05:06', 3);

INSERT INTO public."allergy" VALUES ('lactose');
INSERT INTO public."stockitemallergy" VALUES (1, 'SKU 1', 'lactose');

INSERT INTO public."date" VALUES ('2018-05-28 00:00:00');
INSERT INTO public."expirationdate" VALUES (1, 'SKU 1', '2018-05-28 00:00:00', 1);

SELECT * FROM public."stockitem";

SELECT get_stock_items_filtered (1, null, null, null, null, null);
SELECT get_stock_items_filtered (1, null, 'Mimosa', null, null, null);	--ESPERADO: lista vazia



















--SELECT * FROM public."List"
--SELECT * FROM public."UserList"
--SELECT * FROM public."SystemList"


INSERT INTO public."Category" VALUES (1, 'A'), (2, 'B'), (3, 'C'), (4, 'AA'), (5, 'AQWE')
SELECT * FROM public."Category" WHERE category_name = null
SELECT * FROM public."Product"

SELECT insert_product(3, varchar 'abc', false, 2::int2, varchar 'month');

SELECT * FROM public."StockItem";

SELECT insert_stock_item(
	1,
	1,
	1::int2,
	varchar 'Mimosa',
	varchar 'UHT Magro',
	varchar '1',
	varchar 'l',
	2::int2,
	text 'Leite Magro da Mimosa é do melhor que há!',
	varchar 'Conservar em local fresco após abertura.');

SELECT get_categories('A');








SELECT * FROM public."List" WHERE public."List".list_id % 2 = 0 AND (public."List".list_type = null OR public."List".list_id > 7);