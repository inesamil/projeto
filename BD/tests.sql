
---TESTS---
SELECT delete_user('ze')
SELECT delete_house(1) 


-------------- TEST get_lists_filtered(houseID bigint, system boolean, username character varying(30), shared boolean) --------------
--drops.sql
--creates.sql
--functions.sql

INSERT INTO public."house" VALUES (1, 'house1', '{"house_babiesNumber":1, "house_childrenNumber":0, "house_adultsNumber":2, "house_seniorsNumber":0}');
INSERT INTO public."house" VALUES (2, '{"house_babiesNumber":1, "house_childrenNumber":0, "house_adultsNumber":2, "house_seniorsNumber":0}');
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

-------------- TEST get_movements_filtered (houseID bigint, item_sku character varying(128), type boolean, date date, storageID smallint) --------------
--drops.sql
--creates.sql
--functions.sql

--"\"Leite\",\"Mimosa\",\"UHT Magro\",\"1 l\",07/10/2018",
SELECT * FROM public."stockitem" WHERE public."stockitem".house_id = 1 AND public."stockitem".stockitem_sku = 'P1-Mimosa-UHT Magro-1l';
SELECT * FROM public."expirationdate"
SELECT insert_movement(1::int8, 1::int2, false, 1::int2, 'Leite'::varchar(35), 'Mimosa'::varchar(35), 'UHT Magro'::varchar(35), 1::real, 'l'::varchar(5), null::text,	null::varchar(128), '2018-09-01'::date);
SELECT insert_movement(2::int8, 1::int2, false, 1::int2, 'Manteiga'::varchar(35), 'Becel'::varchar(35), 'Manteiga Vegetal'::varchar(35), 250::real, 'kg'::varchar(5), null::text,	null::varchar(128), '2019-06-01'::date);


SELECT public."list".house_id, public."list".list_id, public."list".list_name,
                public."list".list_type, public."userlist".users_username, public."userlist".list_shareable,
                public."house".house_name
                FROM public."list" JOIN public."userlist" ON (public."list".house_id = public."userlist".house_id
                AND public."list".list_id = public."userlist".list_id)
                JOIN public."house" ON public."house".house_id = public."list".house_id
                WHERE public."list".house_id = ANY (array[1, 2]) AND list_type = 'user' AND users_username = 'pedro'
                --(CASE WHEN ? = true THEN ? ELSE null END)
                UNION
                SELECT public."list".house_id, public."list".list_id, public."list".list_name,
                public."list".list_type, public."userlist".users_username, public."userlist".list_shareable,
                public."house".house_name
                FROM public."list" JOIN public."userlist" ON (public."list".house_id = public."userlist".house_id
                AND public."list".list_id = public."userlist".list_id)
                JOIN public."house" ON public."house".house_id = public."list".house_id
                WHERE public."list".house_id = ANY (array[1, 2]) AND list_type = 'user' AND
                list_shareable = true--(CASE WHEN ? = true THEN true ELSE null END);"
