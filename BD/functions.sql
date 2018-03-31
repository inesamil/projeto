 
-------------------------------------------------------------------------------------------

 -- Procedure to delete a User
 -- DROP FUNCTION delete_user
CREATE OR REPLACE FUNCTION delete_user(username character varying(35)) 
RETURNS VOID AS $$
DECLARE
    cursor_userLists CURSOR FOR SELECT * FROM public."UserList" WHERE user_username = username;
	rec_userList   RECORD;
BEGIN
	-- Remove UserLists and Lists
	OPEN cursor_userLists;
	LOOP
		-- Fetch row into the rec_userList
		FETCH cursor_userLists INTO rec_userList;
		-- exit when no more row to fetch
		EXIT WHEN NOT FOUND;

		-- Delete from UserList (child)
		DELETE FROM public."UserList" WHERE house_id = rec_userList.house_id AND list_id = rec_userList.list_id;
		-- Delete from List (parent)
		DELETE FROM public."List" WHERE house_id = rec_userList.house_id AND list_id = rec_userList.list_id;
	END LOOP;

	-- Close the cursor
	CLOSE cursor_userLists;

	-- Remove User From Houses	
	DELETE FROM public."UserHouse" WHERE user_username = username;

	-- Remove User
	DELETE FROM public."User" WHERE user_username = username;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

 -- Procedure to delete a House
 -- DROP FUNCTION delete_house
CREATE OR REPLACE FUNCTION delete_house(id bigint) 
RETURNS VOID AS $$
BEGIN
	-- Remove StockItemMovement
	DELETE FROM public."StockItemMovement" WHERE house_id = id;

	-- Remove ExpirationDate of House Items	
	DELETE FROM public."ExpirationDate" WHERE house_id = id;

	-- Remove StockItemAllergy
	DELETE FROM public."StockItemAllergy" WHERE house_id = id;

	-- Remove StockItemStorage
	DELETE FROM public."StockItemStorage" WHERE house_id = id;

	-- Remove StockItem
	DELETE FROM public."StockItem" WHERE house_id = id;

	-- Remove Storage
	DELETE FROM public."Storage" WHERE house_id = id;

	-- Remove UserHouse
	DELETE FROM public."UserHouse" WHERE house_id = id;

	-- Remove SystemList
	DELETE FROM public."SystemList" WHERE house_id = id;

	-- Remove UserList
	DELETE FROM public."UserList" WHERE house_id = id;

	-- Remove ListProduct 
	DELETE FROM public."ListProduct" WHERE house_id = id;

	-- Remove List
	DELETE FROM public."List" WHERE house_id = id;

	-- Remove HouseAllergie
	DELETE FROM public."HouseAllergy" WHERE house_id = id;

	-- Remove House
	DELETE FROM public."House" WHERE house_id = id;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-- Procedure to delete a UserList
 -- DROP FUNCTION delete_user_list
CREATE OR REPLACE FUNCTION delete_user_list(houseID bigint, listID smallint) 
RETURNS VOID AS $$
BEGIN
	-- Remove UserList
	DELETE FROM public."UserList" WHERE house_id = houseID AND list_id = listID;

	-- Remove ListProduct 
	DELETE FROM public."ListProduct" WHERE house_id = houseID AND list_id = listID;

	-- Remove List
	DELETE FROM public."List" WHERE house_id = houseID AND list_id = listID;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

 -- Procedure to delete a StockItem
 -- DROP FUNCTION delete_stock_item
CREATE OR REPLACE FUNCTION delete_stock_item(houseID bigint, sku character varying(128)) 
RETURNS VOID AS $$
BEGIN
	-- Remove StockItemAllergy
	DELETE FROM public."StockItemAllergy" WHERE house_id = houseID AND stockItem_sku = sku;

	-- Remove ExpirationDate
	DELETE FROM public."ExpirationDate" WHERE house_id = houseID AND stockItem_sku = sku;

	-- Remove StockItemMovement
	DELETE FROM public."StockItemMovement" WHERE house_id = houseID AND stockItem_sku = sku;
	
	-- Remove StockItemStorage
	DELETE FROM public."StockItemStorage" WHERE house_id = houseID AND stockItem_sku = sku;
	
	-- Remove StockItem
	DELETE FROM public."StockItem" WHERE house_id = houseID AND stockItem_sku = sku;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

 -- Procedure to delete a Storage
 -- DROP FUNCTION delete_storage
CREATE OR REPLACE FUNCTION delete_storage(houseID bigint, storageID smallint) 
RETURNS VOID AS $$
BEGIN
	-- Remove StockItemMovement
	DELETE FROM public."StockItemMovement" WHERE house_id = houseID AND storage_id = storageID;
	
	-- Remove StockItemStorage
	DELETE FROM public."StockItemStorage" WHERE house_id = houseID AND storage_id = storageID;
	
	-- Remove Storage
	DELETE FROM public."StockItem" WHERE house_id = houseID AND storage_id = storageID;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

 -- Procedure to insert a UserList
 -- DROP FUNCTION insert_user_list
CREATE OR REPLACE FUNCTION insert_user_list(houseID bigint, listName character varying(35), username character varying(30), shareable boolean) 
RETURNS VOID AS $$
DECLARE
	listID smallint;
BEGIN
	-- Add List
	INSERT INTO public."List" (house_id, list_name, list_type) VALUES (houseId, listName, 'user') RETURNING list_id INTO listID;

	-- Add UserList
	INSERT INTO public."UserList" (house_id, list_id, user_username, list_shareable) VALUES (houseID, listID, username, shareable);
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

 -- Procedure to insert a SystemList
 -- DROP FUNCTION insert_system_list
CREATE OR REPLACE FUNCTION insert_system_list(houseID bigint, listName character varying(35)) 
RETURNS VOID AS $$
DECLARE 
	listID smallint;
BEGIN
	-- Add List
	INSERT INTO public."List" (house_id, list_name, list_type) VALUES (houseId, listName, 'system') RETURNING list_id INTO listId;

	-- Add UserList
	INSERT INTO public."SystemList" (house_id, list_id) VALUES (houseID, listID);
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-- Function to return Categories whose name matches the received one
-- DROP FUNCTION get_categories
CREATE OR REPLACE FUNCTION get_categories_by_name (name character varying(35))
RETURNS TABLE(id integer, name character varying(35)) AS $$
    SELECT * 
		FROM public."Category"
		WHERE category_name LIKE name || '%'; -- || is the operator for concatenation.
$$ LANGUAGE SQL;

-------------------------------------------------------------------------------------------

-- Function to return Products whose name matches the received one
-- DROP FUNCTION get_products_by_name
CREATE OR REPLACE FUNCTION get_products_by_name (name character varying(35))
RETURNS TABLE(
	category_id integer, 
	product_id integer,
	product_name character varying(35),
	product_edible boolean,
	product_shelfLife smallint,
	product_shelfLifeTimeUnit character varying(35))
AS $$
    SELECT * 
		FROM public."Product"
		WHERE product_name LIKE name || '%'; -- || is the operator for concatenation.
$$ LANGUAGE SQL;

-------------------------------------------------------------------------------------------

-- Function to return Lists filtered by desire
-- DROP FUNCTION get_lists_filtered
CREATE OR REPLACE FUNCTION get_lists_filtered (houseID bigint, system boolean, username character varying(30), shared boolean)
RETURNS TABLE(
	house_id bigint,
	list_id smallint,
	list_name character varying(35),
	list_type character varying(7))
AS $$
    SELECT public."List".house_id, public."List".list_id, public."List".list_name, public."List".list_type
		FROM public."List"
		WHERE list_type = CASE WHEN system = true THEN 'system' ELSE null END
	UNION
	SELECT public."List".house_id, public."List".list_id, public."List".list_name, public."List".list_type
		FROM public."List" JOIN public."UserList" ON public."List".house_id = public."UserList".house_id AND public."List".list_id = public."UserList".list_id
		WHERE list_type = 'user' AND user_username = username
	UNION
	SELECT public."List".house_id, public."List".list_id, public."List".list_name, public."List".list_type
		FROM public."List" JOIN public."UserList" ON public."List".house_id = public."UserList".house_id AND public."List".list_id = public."UserList".list_id
		WHERE list_type = 'user' AND list_shareable = shared;
$$ LANGUAGE SQL;

-------------------------------------------------------------------------------------------



---TESTS---
SELECT delete_user('ze')
SELECT delete_house(1) 


INSERT INTO public."House" VALUES (1, 'house', 0, 0, 1, 0);
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

SELECT get_categories('A')


SELECT insert_system_list(2, 'LIST A');
SELECT insert_system_list(2, 'LIST B'); 
SELECT insert_system_list(2, 'LIST C'); 

SELECT get_lists_filtered (2, true, null, false);
SELECT get_lists_filtered (2, false, 'ze', false);
SELECT get_lists_filtered (2, false, null, true);


SELECT insert_user_list(2, 'USER LIST A', 'ze', true); 
SELECT insert_user_list(2, 'USER LIST B', 'ze', false); 
SELECT insert_user_list(2, 'USER LIST C', 'ze', false); 
									
SELECT insert_user_list(2, 'USER LIST D', 'maria', true); 
SELECT insert_user_list(2, 'USER LIST E', 'maria', true); 
SELECT insert_user_list(2, 'USER LIST F', 'maria', false); 