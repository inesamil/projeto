
---TESTS---
SELECT delete_user('ze')
SELECT delete_house(1) 


INSERT INTO public."House" VALUES (2, 'house2', '{"babiesNumber":1}');
INSERT INTO public."User" VALUES ('ze', 'email', 20, 'ze', '124');
INSERT INTO public."User" VALUES ('maria', 'email.maria', 20, 'maria', '124');
INSERT INTO public."UserHouse" VALUES (1, 'ze', true);
INSERT INTO public."List" VALUES (1, 1, 'list', 'user');
INSERT INTO public."UserList" VALUES (1, 1, 'ze', true);
--SELECT * FROM public."House" 
--SELECT * FROM public."User"
--SELECT * FROM public."UserHouse"
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


SELECT insert_system_list(1, 'LIST A');
SELECT insert_system_list(1, 'LIST B'); 
SELECT insert_system_list(1, 'LIST C'); 
SELECT insert_system_list(2, 'LIST A');
SELECT insert_system_list(2, 'LIST B');

SELECT get_lists_filtered (2, true, null, false);
SELECT get_lists_filtered (2, false, 'ze', false);
SELECT get_lists_filtered (2, false, null, true);


SELECT insert_user_list(2, 'USER LIST A', 'ze', true); 
SELECT insert_user_list(2, 'USER LIST B', 'ze', false); 
SELECT insert_user_list(2, 'USER LIST C', 'ze', false); 
									
SELECT insert_user_list(1, 'USER LIST D', 'maria', true); 
SELECT insert_user_list(2, 'USER LIST E', 'maria', true); 
SELECT insert_user_list(2, 'USER LIST F', 'maria', false); 


SELECT * FROM public."List" WHERE public."List".list_id % 2 = 0 AND (public."List".list_type = null OR public."List".list_id > 7);