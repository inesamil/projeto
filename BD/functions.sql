-- Procedure to delete a User
-- DROP FUNCTION delete_user
CREATE OR REPLACE FUNCTION delete_user(username character varying(30))
RETURNS VOID AS $$
DECLARE
	ids_array integer[];
	userId bigint;
BEGIN
	-- Get userId
	SELECT public."users".users_id INTO userId FROM public."users" WHERE public."users".users_username = username;
	
	-- Save list IDs to remove
	ids_array := ARRAY(SELECT public."userlist".list_id FROM public."userlist" WHERE public."userlist".users_id = userId);
	
	-- Remove UserLists
	DELETE FROM public."userlist" WHERE public."userlist".users_id = userId;
	
	-- Remove Lists
	DELETE FROM public."list" WHERE list_id IN (select(unnest(ids_array)));
	
	-- Remove User From Houses	
	DELETE FROM public."userhouse" WHERE public."userlist".users_id = userId;

	-- Remove User
	DELETE FROM public."users" WHERE public."userlist".users_id = userId;
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
	house_name character varying(35),
	house_characteristics json,
	list_id smallint,
	list_name character varying(35),
	list_type character varying(7),
	user_id bigint,
	users_username character varying(30),
	users_email character varying(254),
	users_age smallint,
	users_name character varying(70),
	users_password character varying(60),
	list_shareable boolean
) AS $$
DECLARE
	listID smallint;
	userId bigint;
BEGIN
	-- Get last id
	SELECT public."list".list_id FROM public."list" WHERE public."list".house_id = houseID ORDER BY public."list".list_id DESC LIMIT 1 INTO listID;
	IF listID IS NULL THEN
		listID := 1; 	-- First list inserted
	ELSE
		listID := listID + 1;	-- Increment
	END IF;
	
	-- Get user id
	SELECT public."users".users_id INTO userId FROM public."users" WHERE public."users".users_username = username;
	
	-- Add List
	INSERT INTO public."list" (house_id, list_id, list_name, list_type) VALUES (houseId, listID, listName, 'user');

	-- Add UserList
	INSERT INTO public."userlist" (house_id, list_id, users_id, list_shareable) VALUES (houseID, listID, userId, shareable);
	
	RETURN query
	SELECT public."userlist".house_id, public."house".house_name, public."house".house_characteristics,
			public."userlist".list_id, public."list".list_name, public."list".list_type, 
			public."userlist".users_id, public."users".users_username, public."users".users_email, public."users".users_age, public."users".users_name, public."users".users_password,
			public."userlist".list_shareable 
		FROM public."list" JOIN public."userlist" ON public."list".house_id = public."userlist".house_id AND public."list".list_id = public."userlist".list_id
			JOIN public."house" ON public."list".house_id = public."house".house_id
			JOIN public."users" ON public."userlist".users_id = public."users".users_id
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

-- Procedure to insert a Movement
-- DROP FUNCTION insert_movement
CREATE OR REPLACE FUNCTION insert_movement(
	houseId bigint,
	storageId smallint,
	movementType bool,
	movementQuantity smallint,
	productName character varying(35),	-- Tag Info
	brand character varying(35),	-- Tag Info
	variety character varying(35),	-- Tag Info
	segment real,	-- Tag Info
	segmentUnit character varying(5),	-- Tag Info
	description text,	-- Tag Info
	conservationStorage character varying(128),	-- Tag Info
	expirationDatexpto date)	-- Tag Info
RETURNS TABLE(
	house_id bigint,
	stockitem_sku character varying(128),
	storage_id smallint,
	stockitemmovement_type boolean,
	stockitemmovement_datetime timestamp,
	stockitemmovement_quantity smallint,
	stockitemmovement_finalquantity smallint
) AS $$
DECLARE 
	productId integer;
	quantity smallint;
	finalQuantity smallint;
	sku character varying(128) = 0;
	movementDatetime timestamp;
BEGIN
	-- Get product ID
	SELECT public."product".product_id INTO productId FROM public."product" WHERE public."product".product_name = productName;
	
	-- Generate SKU
	sku := generate_sku(productId, brand, variety, segment, segmentUnit);
	
	IF EXISTS (SELECT * FROM public."stockitem" WHERE public."stockitem".house_id = houseId AND public."stockitem".stockitem_sku = sku) THEN
		-- StockItem exists in the house
		-- Update StockItem quantity
		quantity := movementQuantity;
		IF movementType = false THEN
			quantity = quantity * -1;
		END IF;
		UPDATE public."stockitem" SET stockitem_quantity = public."stockitem".stockitem_quantity + quantity 
			WHERE public."stockitem".house_id = houseId AND public."stockitem".stockitem_sku = sku;
		
		-- Update StockItemQuantity in Storage
		UPDATE public."stockitemstorage" SET stockitemstorage_quantity = public."stockitemstorage".stockitemstorage_quantity + quantity
			WHERE public."stockitemstorage".house_id = houseId AND public."stockitemstorage".storage_id = storageId;
			
		-- Update quantity expiring
		UPDATE public."expirationdate" SET date_quantity = public."expirationdate".date_quantity + quantity 
			WHERE public."expirationdate".house_id = houseId AND public."expirationdate".stockitem_sku = sku
				AND public."expirationdate".date_date = expirationDatexpto;
	ELSE
		-- StockItem does not exist in the house
		-- Add StockItem
		INSERT INTO public."stockitem" (house_id, stockitem_sku, product_id, stockitem_brand, stockitem_variety, stockitem_segment,
										stockitem_segmentunit, stockitem_quantity, stockitem_description, stockitem_conservationstorage)
			VALUES (houseId, sku, productId, brand, variety, segment, segmentUnit, movementQuantity, description, conservationStorage);
	
		-- Add StockItem in Storage
		INSERT INTO public."stockitemstorage" (house_id, stockitem_sku, storage_id, stockitemstorage_quantity) 
			VALUES (houseId, sku, storageId, movementQuantity);
		
	   	IF NOT EXISTS (SELECT * FROM public."date" WHERE public."date".date_date = expirationDatexpto) THEN
		-- Insert Date
			INSERT INTO public."date" (date_date)
				VALUES (expirationDatexpto);
		END IF;
		-- Insert Expiration Date 
		INSERT INTO public."expirationdate" (house_id, stockitem_sku, date_date, date_quantity)
			VALUES (houseId, sku, expirationDatexpto, movementQuantity);
	END IF;
	
	movementDatetime := CURRENT_TIMESTAMP;
	
	-- Get final quantity
	SELECT public."stockitem".stockitem_quantity INTO finalQuantity 
		FROM public."stockitem"
		WHERE public."stockitem".house_id = houseId AND public."stockitem".stockitem_sku = sku;
	
	-- Insert Movement
	INSERT INTO public."stockitemmovement" (house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime, stockitemmovement_quantity, stockitemmovement_finalquantity)
		VALUES (houseId, sku, storageId, movementType, movementDatetime, movementQuantity, finalQuantity);

	RETURN query
	SELECT public."stockitemmovement".house_id, public."stockitemmovement".stockitem_sku, public."stockitemmovement".storage_id, public."stockitemmovement".stockitemmovement_type, 
		public."stockitemmovement".stockitemmovement_datetime, public."stockitemmovement".stockitemmovement_quantity, public."stockitemmovement".stockitemmovement_finalquantity
	FROM public."stockitemmovement"
	WHERE public."stockitemmovement".house_id = houseId AND public."stockitemmovement".stockitem_sku = sku AND public."stockitemmovement".storage_id = storageId AND public."stockitemmovement".stockitemmovement_type = movementType 
		AND public."stockitemmovement".stockitemmovement_datetime = movementDatetime AND public."stockitemmovement".stockitemmovement_quantity = movementQuantity;
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