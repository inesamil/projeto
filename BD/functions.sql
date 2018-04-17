 -- Procedure to insert a UserList
 -- DROP FUNCTION insert_user_list
 ------------------------------------------------------------feito-------------------------------------------------------------
CREATE OR REPLACE FUNCTION insert_user_list(houseID bigint, listName character varying(35), username character varying(30), shareable boolean) 
RETURNS VOID AS $$
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
    SELECT public."List".house_id, public."List".list_id, public."List".list_name, public."List".list_type
		FROM public."List"
		WHERE list_type = CASE WHEN system = true THEN 'system' ELSE null END
	UNION
	SELECT public."List".house_id, public."List".list_id, public."List".list_name, public."List".list_type
		FROM public."List" JOIN public."UserList" ON (public."List".house_id = public."UserList".house_id AND public."List".list_id = public."UserList".list_id)
		WHERE list_type = 'user' AND user_username = username
	UNION
	SELECT public."List".house_id, public."List".list_id, public."List".list_name, public."List".list_type
		FROM public."List" JOIN public."UserList" ON (public."List".house_id = public."UserList".house_id AND public."List".list_id = public."UserList".list_id)
		WHERE list_type = 'user' AND list_shareable = shared;
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
    SELECT public."StockItem".house_id, public."StockItem".stockItem_sku, public."StockItem".category_id, public."StockItem".product_id, public."StockItem".stockItem_brand, 
			public."StockItem".stockItem_segment, public."StockItem".stockItem_variety, public."StockItem".stockItem_quantity, public."StockItem".stockItem_segmentUnit, 
			public."StockItem".stockItem_description, public."StockItem".stockItem_conservationStorage
		FROM public."StockItem" JOIN public."Product" ON (public."StockItem".category_id = public."Product".category_id AND public."StockItem".product_id = public."Product".product_id)
			JOIN public."StockItemStorage" ON (public."StockItem".house_id = public."StockItemStorage".house_id AND public."StockItem".stockItem_sku = public."StockItemStorage".stockItem_sku)
		WHERE public."StockItem".house_id = houseID AND (public."Product".product_name = productName OR public."StockItem".stockItem_brand = brand OR public."StockItem".stockItem_variety = variety
			OR public."StockItem".stockItem_segment = segment OR public."StockItemStorage".storage_id = storageID)
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
    SELECT public."StockItemMovement".house_id, public."StockItemMovement".stockItem_sku, public."StockItemMovement".storage_id, public."StockItemMovement".stockItemMovement_type, 
		public."StockItemMovement".stockItemMovement_dateTime, public."StockItemMovement".stockItemMovement_quantity
		FROM public."StockItemMovement"
		WHERE public."StockItemMovement".house_id = houseID 
			AND (public."StockItemMovement".stockItem_sku = item_sku 
			OR (type IS NOT NULL AND public."StockItemMovement".stockItemMovement_type = type)
			OR DATE(public."StockItemMovement".stockItemMovement_dateTime) = date
			OR public."StockItemMovement".storage_id = storageID)
$$ LANGUAGE SQL;

-------------------------------------------------------------------------------------------
