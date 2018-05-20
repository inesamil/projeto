
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

SELECT get_lists_filtered (1, true, null, false);	-- EXPECTED: Listas de sistema
SELECT get_lists_filtered (1, true, null, null);	-- EXPECTED: Listas de sistema
SELECT get_lists_filtered (1, true, null, true);	-- EXPECTED: Listas de sistema e Listas partilhadas na casa
SELECT get_lists_filtered (1, true, 'ze', false);	-- EXPECTED: Listas de sistema, Listas do Zé
SELECT get_lists_filtered (1, true, 'ze', true);	-- EXPECTED: Listas de sistema, Listas do Zé e Listas partilhadas na casa
SELECT get_lists_filtered (1, false, 'ze', false);	-- EXPECTED: Listas do Zé
SELECT get_lists_filtered (1, false, 'ze', true);	-- EXPECTED: Listas do Zé e Listas partilhadas na casa
SELECT get_lists_filtered (1, false, 'maria', false);	-- EXPECTED: Listas da Maria
SELECT get_lists_filtered (1, false, null, true);	-- EXPECTED: Listas partilhadas na casa


-------------- TEST get_stock_items_filtered (houseID bigint, productName character varying(35), brand character varying(35), variety character varying(35), segment real, storageID smallint) --------------
--drops.sql
--creates.sql
--functions.sql

INSERT INTO public."house" VALUES (1, 'house1', '{"babiesNumber":1}');
SELECT * FROM public."house"; 

INSERT INTO public."category" VALUES (1, 'Lacticínios'), (2, 'Fruta');
INSERT INTO public."product" VALUES  (1, 1, 'Leite de Vaca', true, 3, 'day'), (2, 1, 'Maçã', true, 2, 'month'), (2, 2, 'Laranja', true, 2, 'month');

INSERT INTO public."storage" VALUES (1, 1, 'Storage 1', numrange(0.0, 5.0, '[]'));

INSERT INTO public."stockitem" VALUES (1, 'SKU_1', 1, 1, 'Mimosa', 1, 'UHT Magro', 1, 'l', 'Leite Magro da Mimosa é do melhor que há!', 'Conservar em local fresco após abertura.'),
	(1, 'SKU_2', 2, 1, 'Fruta Del Monte', 1, 'Golden', 5, 'units', 'Maçãs Del Monte.', 'Conservar em local seco.');

INSERT INTO public."stockitemstorage" VALUES (1, 'SKU_1', 1, 1), (1, 'SKU_2', 1, 3);

INSERT INTO public."stockitemmovement" VALUES (1, 'SKU_1', 1, true, '2018-04-18 08:02:31', 1), (1, 'SKU_2', 1, true, '2018-04-18 19:05:06', 3);

INSERT INTO public."allergy" VALUES ('lactose');
INSERT INTO public."stockitemallergy" VALUES (1, 'SKU_1', 'lactose');

INSERT INTO public."date" VALUES ('2018-05-28');
INSERT INTO public."expirationdate" VALUES (1, 'SKU_1', '2018-05-28', 1);

SELECT * FROM public."stockitem";

SELECT get_stock_items_filtered (1, null, null, null, null, null);	-- EXPECTED: Todos os itens em stock na casa
SELECT get_stock_items_filtered (1, 'Leite de Vaca', null, null, null, null);	-- EXPECTED: Todos os itens em casa que são Leite de Vaca 
SELECT get_stock_items_filtered (1, 'Leite de Vaca', null, null, null, 1::int2);	-- EXPECTED: Todos os itens em casa que são Leite de Vaca e estão armazenados no local de armazenamento com ID = 1
SELECT get_stock_items_filtered (1, null, 'Mimosa', null, null, null);	-- EXPECTED: Todos os itens em stock na casa da marca 'Mimosa'
SELECT get_stock_items_filtered (1, null, null, 'UHT Magro', null, null);	-- EXPECTED: Todos os itens em stock na casa da variedade 'UHT Magro'
SELECT get_stock_items_filtered (1, null, null, null, 1, null);	-- EXPECTED: Todos os itens em stock na casa com segmento 1
SELECT get_stock_items_filtered (1, null, null, null, null, 1::int2);	-- EXPECTED: Todos os itens em stock na casa armazenados no local de armazenamento com ID = 1
SELECT get_stock_items_filtered (1, 'Maçãs', 'Mimosa', null, null, 1::int2);	-- EXPECTED: Vazio. Todos os itens que são maçãs e são da marca 'Mimosa'


-------------- TEST get_movements_filtered (houseID bigint, item_sku character varying(128), type boolean, date date, storageID smallint) --------------
--drops.sql
--creates.sql
--functions.sql

INSERT INTO public."house" VALUES (1, 'house1', '{"babiesNumber":1}');
SELECT * FROM public."house"; 

INSERT INTO public."category" VALUES (1, 'Lacticínios'), (2, 'Fruta');
INSERT INTO public."product" VALUES  (1, 1, 'Leite de Vaca', true, 3, 'day'), (2, 1, 'Maçã', true, 2, 'month'), (2, 2, 'Laranja', true, 2, 'month');

INSERT INTO public."storage" VALUES (1, 1, 'Storage 1', numrange(0.0, 5.0));

INSERT INTO public."stockitem" VALUES (1, 'SKU_1', 1, 1, 'Mimosa', 1, 'UHT Magro', 1, 'l', 'Leite Magro da Mimosa é do melhor que há!', 'Conservar em local fresco após abertura.'),
	(1, 'SKU_2', 2, 1, 'Fruta Del Monte', 1, 'Golden', 5, 'units', 'Maçãs Del Monte.', 'Conservar em local seco.');

INSERT INTO public."stockitemstorage" VALUES (1, 'SKU_1', 1, 1), (1, 'SKU_2', 1, 3);

INSERT INTO public."stockitemmovement" VALUES (1, 'SKU_1', 1, true, '2018-04-18 08:02:31', 1),
	(1, 'SKU_2', 1, true, '2018-04-18 19:05:06', 3), (1, 'SKU_2', 1, false, '2018-04-18 20:35:15', 1), (1, 'SKU_2', 1, true, '2018-04-18 20:36:02', 2);


SELECT get_movements_filtered (1, null, null, null, null);	-- EXPECTED: Todos os movimentos na casa
SELECT get_movements_filtered (1, 'SKU 2', null, null, null);	-- EXPECTED: Todos os movimentos do item com sku = 'SKU 2'
SELECT get_movements_filtered (1, null, true, null, null);	-- EXPECTED: Todos os movimentos de entrada
SELECT get_movements_filtered (1, null, null, '2018-04-18', null);	-- EXPECTED: Todos os movimentos do dia 18
SELECT get_movements_filtered (1, null, null, null, 1::int2);	-- EXPECTED: Todos os movimentos relacionados com o local de armazenamento com ID = 1
SELECT get_movements_filtered (1, 'SKU 2', false, null, null);	-- EXPECTED: Todos os movimentos do item com sku = 'SKU 2' e de saída

