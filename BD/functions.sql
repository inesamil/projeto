 -- Procedure to insert a UserList
 -- DROP FUNCTION insert_user_list
 ------------------------------------------------------------feito-------------------------------------------------------------
CREATE OR REPLACE FUNCTION insert_user_list(houseID bigint, listName character varying(35), username character varying(30), shareable boolean) 
RETURNS integer AS $$
DECLARE
	listID smallint;
BEGIN
	-- Get last id
	SELECT list_id FROM public."list" WHERE house_id = houseID ORDER BY list_id DESC LIMIT 1 INTO listID;
	IF listID IS NULL THEN
		listID := 1; 	-- First list inserted
	ELSE
		listID := listID + 1;	-- Increment
	END IF;
	
	-- Add List
	INSERT INTO public."list" (house_id, list_id, list_name, list_type) VALUES (houseId, listID, listName, 'user');

	-- Add UserList
	INSERT INTO public."userlist" (house_id, list_id, users_username, list_shareable) VALUES (houseID, listID, username, shareable);
	
	RETURN listID;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

 -- Procedure to insert a SystemList
 -- DROP FUNCTION insert_system_list
 ------------------------------------------------------------feito-------------------------------------------------------------
CREATE OR REPLACE FUNCTION insert_system_list(houseID bigint, listName character varying(35)) 
RETURNS VOID AS $$
DECLARE 
	listID smallint = 0;
BEGIN
	-- Get last id
	SELECT list_id FROM public."list" WHERE house_id = houseID ORDER BY list_id DESC LIMIT 1 INTO listID;
	IF listID IS NULL THEN
		listID := 1; 	-- First list inserted
	ELSE
		listID := listID + 1;	-- Increment
	END IF;
		
	-- Add List
	INSERT INTO public."list" (house_id, list_id, list_name, list_type) VALUES (houseId, listID, listName, 'system');

	-- Add UserList
	INSERT INTO public."systemlist" (house_id, list_id) VALUES (houseID, listID);
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to insert a Product
-- DROP FUNCTION insert_product
------------------------------------------------------------feito-------------------------------------------------------------
CREATE OR REPLACE FUNCTION insert_product(categoryID integer, designation character varying(35), edible boolean, shelfLife smallint, shelfLifeTimeUnit character varying(35)) 
RETURNS VOID AS $$
DECLARE 
	productID smallint = 0;
BEGIN
	-- Get last id
	SELECT product_id FROM public."product" WHERE category_id = categoryID ORDER BY product_id DESC LIMIT 1 INTO productID;
	IF productID IS NULL THEN
		productID := 1; 	-- First list inserted
	ELSE
		productID := productID + 1;	-- Increment
	END IF;
		
	-- Add Product
	INSERT INTO public."product" (category_id, product_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit) 
		VALUES (categoryID, productID, designation, edible, shelfLife, shelfLifeTimeUnit);
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to insert a StockItem
-- DROP FUNCTION insert_stock_item
------------------------------------------------------------feito-------------------------------------------------------------
CREATE OR REPLACE FUNCTION insert_stock_item(
	houseID bigint,
	categoryID integer,
	productID integer,
	brand character varying(35),
	variety character varying(35),
	segment real,
	segmentUnit character varying(5),
	quantity smallint,
	description text,
	conservationStorage character varying(128)) 
RETURNS VOID AS $$
DECLARE 
	sku character varying(128) = 0;
BEGIN
	-- Generate SKU
	sku := generate_sku(categoryID, productID, brand, variety, segment, segmentUnit);
	
		
	-- Add StockItem
	INSERT INTO public."stockitem" (house_id, stockitem_sku, category_id, product_id, stockitem_brand, stockitem_variety, stockitem_segment,
										stockitem_segmentUnit, stockitem_quantity, stockitem_description, stockitem_conservationStorage) 
		VALUES (houseID, sku, categoryID, productID, brand, variety, segment, segmentUnit, quantity, description, conservationStorage);
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to generate a SKU
-- DROP FUNCTION generate_sku
------------------------------------------------------------feito-------------------------------------------------------------
CREATE OR REPLACE FUNCTION generate_sku(
	categoryID integer,
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
	sku := 'C' || categoryID || 'P' || productID || '-' || brand || '-' || variety || '-' || segment || segmentUnit;
	
	return sku;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Procedure to insert a Storage
-- DROP FUNCTION insert_storage
------------------------------------------------------------feito-------------------------------------------------------------
CREATE OR REPLACE FUNCTION insert_storage(houseID bigint, designation character varying(35), temperature numrange) 
RETURNS VOID AS $$
DECLARE 
	storageID smallint = 0;
BEGIN
	-- Get last id
	SELECT storage_id FROM public."storage" WHERE house_id = houseID ORDER BY storage_id DESC LIMIT 1 INTO storageID;
	IF storageID IS NULL THEN
		storageID := 1; 	-- First list inserted
	ELSE
		storageID := storageID + 1;	-- Increment
	END IF;
		
	-- Add Product
	INSERT INTO public."storage" (house_id, storage_id, storage_name, storage_temperature) 
		VALUES (houseID, storageID, designation, temperature);
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Function to return Lists filtered
-- DROP FUNCTION get_lists_filtered
CREATE OR REPLACE FUNCTION get_lists_filtered (houseID bigint, system boolean, username character varying(30), shared boolean)
RETURNS TABLE(
	house_id bigint,
	list_id smallint,
	list_name character varying(35),
	list_type character varying(7))
AS $$
    SELECT public."list".house_id, public."list".list_id, public."list".list_name, public."list".list_type
		FROM public."list"
		WHERE house_id = houseID AND list_type = CASE WHEN system = true THEN 'system' ELSE null END
	UNION
	SELECT public."list".house_id, public."list".list_id, public."list".list_name, public."list".list_type
		FROM public."list" JOIN public."userlist" ON (public."list".house_id = public."userlist".house_id AND public."list".list_id = public."userlist".list_id)
		WHERE house_id = houseID AND list_type = 'user' AND users_username = username
	UNION
	SELECT public."list".house_id, public."list".list_id, public."list".list_name, public."list".list_type
		FROM public."list" JOIN public."userlist" ON (public."list".house_id = public."userlist".house_id AND public."list".list_id = public."userlist".list_id)
		WHERE house_id = houseID AND list_type = 'user' AND list_shareable = shared;
$$ LANGUAGE SQL;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Function to return Stock Items filtered
-- DROP FUNCTION get_stock_items_filtered
CREATE OR REPLACE FUNCTION get_stock_items_filtered (houseID bigint, productName character varying(35), brand character varying(35), variety character varying(35), 
													segment real, storageID smallint)
RETURNS TABLE(
	house_id bigint,
	stockItem_sku character varying(128),
	category_id integer,
	product_id integer,
	stockItem_brand character varying(35),
	stockItem_segment real,
	stockItem_variety character varying(35),
	stockItem_quantity smallint,
	stockItem_segmentUnit character varying(5),
	stockItem_description text,
	stockItem_conservationStorage character varying(128))
AS $$
    SELECT public."stockitem".house_id, public."stockitem".stockitem_sku, public."stockitem".category_id, public."stockitem".product_id, public."stockitem".stockitem_brand, 
			public."stockitem".stockitem_segment, public."stockitem".stockitem_variety, public."stockitem".stockitem_quantity, public."stockitem".stockitem_segmentunit, 
			public."stockitem".stockitem_description, public."stockitem".stockitem_conservationstorage
		FROM public."stockitem" JOIN public."product" ON (public."stockitem".category_id = public."product".category_id AND public."stockitem".product_id = public."product".product_id)
			JOIN public."stockitemstorage" ON (public."stockitem".house_id = public."stockitemstorage".house_id AND public."stockitem".stockitem_sku = public."stockitemstorage".stockitem_sku)
		WHERE public."stockitem".house_id = houseID AND (public."product".product_name = productName OR public."stockitem".stockitem_brand = brand OR public."stockitem".stockitem_variety = variety
			OR public."stockitem".stockitem_segment = segment OR public."stockitemstorage".storage_id = storageID)
$$ LANGUAGE SQL;

-------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------

-- Function to return Movements filtered
-- DROP FUNCTION get_movements_filtered
CREATE OR REPLACE FUNCTION get_movements_filtered (houseID bigint, item_sku character varying(128), type boolean, date date, storageID smallint)
RETURNS TABLE(
	house_id bigint,
	stockItem_sku character varying(128),
	storage_id smallint,
	stockItemMovement_type boolean,
	stockItemMovement_dateTime timestamp,
	stockItemMovement_quantity smallint)
AS $$
    SELECT public."stockitemmovement".house_id, public."stockitemmovement".stockitem_sku, public."stockitemmovement".storage_id, public."stockitemmovement".stockitemmovement_type, 
		public."stockitemmovement".stockitemmovement_datetime, public."stockitemmovement".stockitemmovement_quantity
		FROM public."stockitemmovement"
		WHERE public."stockitemmovement".house_id = houseID 
			AND (public."stockitemmovement".stockitem_sku = item_sku 
			OR (type IS NOT NULL AND public."stockitemmovement".stockitemmovement_type = type)
			OR DATE(public."stockitemmovement".stockitemmovement_datetime) = date
			OR public."stockitemmovement".storage_id = storageID)
$$ LANGUAGE SQL;

-------------------------------------------------------------------------------------------
