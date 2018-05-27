-- Procedure to delete a User
-- DROP FUNCTION delete_user
CREATE OR REPLACE FUNCTION delete_user(username character varying(35))
RETURNS VOID AS $$
DECLARE
	ids_array integer[];
BEGIN
	-- Save list IDs to remove
	ids_array := ARRAY(SELECT public."userlist".list_id FROM public."userlist" WHERE public."userlist".users_username = username);
	
	-- Remove UserLists
	DELETE FROM public."userlist" WHERE users_username = username;
	
	-- Remove Lists
	DELETE FROM public."list" WHERE list_id IN (select(unnest(ids_array)));
	
	-- Remove User From Houses	
	DELETE FROM public."userhouse" WHERE users_username = username;

	-- Remove User
	DELETE FROM public."users" WHERE users_username = username;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to delete a House
-- DROP FUNCTION delete_house
CREATE OR REPLACE FUNCTION delete_house(id bigint) 
RETURNS VOID AS $$
BEGIN
	-- Remove StockItemMovement
	DELETE FROM public."stockitemmovement" WHERE house_id = id;

	-- Remove ExpirationDate of House Items	
	DELETE FROM public."expirationdate" WHERE house_id = id;

	-- Remove StockItemAllergy
	DELETE FROM public."stockitemallergy" WHERE house_id = id;

	-- Remove StockItemStorage
	DELETE FROM public."stockitemstorage" WHERE house_id = id;

	-- Remove StockItem
	DELETE FROM public."stockitem" WHERE house_id = id;

	-- Remove Storage
	DELETE FROM public."storage" WHERE house_id = id;

	-- Remove UserHouse
	DELETE FROM public."userhouse" WHERE house_id = id;

	-- Remove SystemList
	DELETE FROM public."systemlist" WHERE house_id = id;

	-- Remove UserList
	DELETE FROM public."userlist" WHERE house_id = id;

	-- Remove ListProduct 
	DELETE FROM public."listproduct" WHERE house_id = id;

	-- Remove List
	DELETE FROM public."list" WHERE house_id = id;

	-- Remove HouseAllergie
	DELETE FROM public."houseallergy" WHERE house_id = id;

	-- Remove House
	DELETE FROM public."house" WHERE house_id = id;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to delete a UserList
-- DROP FUNCTION delete_user_list
CREATE OR REPLACE FUNCTION delete_user_list(houseID bigint, listID smallint) 
RETURNS VOID AS $$
BEGIN
	-- Remove UserList
	DELETE FROM public."userlist" WHERE house_id = houseID AND list_id = listID;

	-- Remove ListProduct 
	DELETE FROM public."listproduct" WHERE house_id = houseID AND list_id = listID;

	-- Remove List
	DELETE FROM public."list" WHERE house_id = houseID AND list_id = listID;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to delete a StockItem
-- DROP FUNCTION delete_stock_item
CREATE OR REPLACE FUNCTION delete_stock_item(houseID bigint, sku character varying(128)) 
RETURNS VOID AS $$
BEGIN
	-- Remove StockItemAllergy
	DELETE FROM public."stockitemallergy" WHERE house_id = houseID AND stockitem_sku = sku;

	-- Remove ExpirationDate
	DELETE FROM public."expirationdate" WHERE house_id = houseID AND stockitem_sku = sku;

	-- Remove StockItemMovement
	DELETE FROM public."stockitemmovement" WHERE house_id = houseID AND stockitem_sku = sku;
	
	-- Remove StockItemStorage
	DELETE FROM public."stockitemstorage" WHERE house_id = houseID AND stockitem_sku = sku;
	
	-- Remove StockItem
	DELETE FROM public."stockitem" WHERE house_id = houseID AND stockitem_sku = sku;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to delete a Storage
 -- DROP FUNCTION delete_storage
CREATE OR REPLACE FUNCTION delete_storage(houseID bigint, storageID smallint) 
RETURNS VOID AS $$
BEGIN
	-- Remove StockItemMovement
	DELETE FROM public."stockitemmovement" WHERE house_id = houseID AND storage_id = storageID;
	
	-- Remove StockItemStorage
	DELETE FROM public."stockitemstorage" WHERE house_id = houseID AND storage_id = storageID;
	
	-- Remove Storage
	DELETE FROM public."storage" WHERE house_id = houseID AND storage_id = storageID;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

 -- Procedure to insert a UserList
 -- DROP FUNCTION insert_user_list
CREATE OR REPLACE FUNCTION insert_user_list(houseID bigint, listName character varying(35), username character varying(30), shareable boolean) 
RETURNS TABLE(
	house_id bigint,
	list_id smallint,
	list_name character varying(35),
	users_username character varying(30),
	list_shareable boolean
) AS $$
DECLARE
	listID smallint;
BEGIN
	-- Get last id
	SELECT public."list".list_id FROM public."list" WHERE public."list".house_id = houseID ORDER BY public."list".list_id DESC LIMIT 1 INTO listID;
	IF listID IS NULL THEN
		listID := 1; 	-- First list inserted
	ELSE
		listID := listID + 1;	-- Increment
	END IF;
	
	-- Add List
	INSERT INTO public."list" (house_id, list_id, list_name, list_type) VALUES (houseId, listID, listName, 'user');

	-- Add UserList
	INSERT INTO public."userlist" (house_id, list_id, users_username, list_shareable) VALUES (houseID, listID, username, shareable);
	
	RETURN query
	SELECT public."userlist".house_id, public."userlist".list_id, public."list".list_name, public."userlist".users_username, public."userlist".list_shareable FROM public."list"
	JOIN public."userlist" ON public."list".house_id = public."userlist".house_id AND public."list".list_id = public."userlist".list_id
	WHERE public."list".house_id = houseID AND public."list".list_id = listID;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

 -- Procedure to insert a SystemList
 -- DROP FUNCTION insert_system_list
CREATE OR REPLACE FUNCTION insert_system_list(houseID bigint, listName character varying(35))
RETURNS TABLE(
	house_id bigint,
	list_id smallint,
	list_name character varying(35)
) AS $$
DECLARE 
	listID smallint;
BEGIN
	-- Get last id
	SELECT public."list".list_id FROM public."list" WHERE public."list".house_id = houseID ORDER BY public."list".list_id DESC LIMIT 1 INTO listID;
	IF listID IS NULL THEN
		listID := 1; 	-- First list inserted
	ELSE
		listID := listID + 1;	-- Increment
	END IF;
		
	-- Add List
	INSERT INTO public."list" (house_id, list_id, list_name, list_type) VALUES (houseId, listID, listName, 'system');

	-- Add UserList
	INSERT INTO public."systemlist" (house_id, list_id) VALUES (houseID, listID);
	
	RETURN query
	SELECT public."list".house_id, public."list".list_id, public."list".list_name FROM public."list"
	JOIN public."systemlist" ON public."list".house_id = public."systemlist".house_id AND public."list".list_id = public."systemlist".list_id
	WHERE public."list".house_id = houseID AND public."list".list_id = listID;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to insert a StockItem
-- DROP FUNCTION insert_stock_item
CREATE OR REPLACE FUNCTION insert_stock_item(
	houseID bigint,
	productID integer,
	brand character varying(35),
	variety character varying(35),
	segment real,
	segmentUnit character varying(5),
	quantity smallint,
	description text,
	conservationStorage character varying(128)) 
RETURNS TABLE(
	house_id bigint,
	stockitem_sku character varying(128),
	product_id integer,
	stockitem_brand character varying(35),
	stockitem_segment real,
	stockitem_variety character varying(35),
	stockitem_quantity smallint,
	stockitem_segmentunit character varying(5),
	stockitem_description text,
	stockitem_conservationstorage character varying(128)
) AS $$
DECLARE 
	sku character varying(128) = 0;
BEGIN
	-- Generate SKU
	sku := generate_sku(productID, brand, variety, segment, segmentUnit);
	
		
	-- Add StockItem
	INSERT INTO public."stockitem" (house_id, stockitem_sku, product_id, stockitem_brand, stockitem_variety, stockitem_segment,
										stockitem_segmentUnit, stockitem_quantity, stockitem_description, stockitem_conservationStorage) 
		VALUES (houseID, sku, productID, brand, variety, segment, segmentUnit, quantity, description, conservationStorage);
		
	RETURN query
	SELECT public."stockitem".house_id, public."stockitem".stockitem_sku, public."stockitem".product_id, public."stockitem".stockitem_brand,
			public."stockitem".stockitem_segment, public."stockitem".stockitem_variety, public."stockitem".stockitem_quantity, public."stockitem".stockitem_segmentunit,
			public."stockitem".stockitem_description, public."stockitem".stockitem_conservationstorage
	FROM public."stockitem"
	WHERE public."stockitem".house_id = houseID AND public."stockitem".stockitem_sku = sku;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to generate a SKU
-- DROP FUNCTION generate_sku
CREATE OR REPLACE FUNCTION generate_sku(
	productID integer,
	brand character varying(35),
	variety character varying(35),
	segment real,
	segmentUnit character varying(5)) 
RETURNS VARCHAR(128) AS $$
DECLARE 
	sku character varying(128) = 0;
BEGIN
	-- Generate SKU
	sku := 'P' || productID || '-' || brand || '-' || variety || '-' || segment || segmentUnit;
	
	RETURN sku;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to insert a Storage
-- DROP FUNCTION insert_storage
CREATE OR REPLACE FUNCTION insert_storage(houseID bigint, designation character varying(35), temperature numrange) 
RETURNS TABLE(
	house_id bigint,
	storage_id smallint,
	storage_name character varying(35),
	storage_temperature numrange
) AS $$
DECLARE 
	storageID smallint = 0;
BEGIN
	-- Get last id
	SELECT public."storage".storage_id FROM public."storage" WHERE public."storage".house_id = houseID ORDER BY public."storage".storage_id DESC LIMIT 1 INTO storageID;
	IF storageID IS NULL THEN
		storageID := 1; 	-- First list inserted
	ELSE
		storageID := storageID + 1;	-- Increment
	END IF;
		
	-- Add Product
	INSERT INTO public."storage" (house_id, storage_id, storage_name, storage_temperature) 
		VALUES (houseID, storageID, designation, temperature);
		
	RETURN query
	SELECT public."storage".house_id, public."storage".storage_id, public."storage".storage_name, public."storage".storage_temperature FROM public."storage"
	WHERE public."storage".house_id = houseID AND public."storage".storage_id = storageID;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------