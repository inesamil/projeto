CREATE TABLE IF NOT EXISTS public."House" (
	house_id bigserial NOT NULL PRIMARY KEY,
	house_name character varying(35) NOT NULL,
	house_babiesNumber smallint NOT NULL CHECK (house_babiesNumber BETWEEN 0 AND 100),
	house_childrenNumber smallint NOT NULL CHECK (house_childrenNumber BETWEEN 0 AND 100),
	house_adultsNumber smallint NOT NULL CHECK (house_adultsNumber BETWEEN 0 AND 100),
	house_seniorsNumber smallint NOT NULL CHECK (house_seniorsNumber BETWEEN 0 AND 100)
);

CREATE TABLE IF NOT EXISTS public."User" (
	user_username character varying(30) NOT NULL PRIMARY KEY,
	user_email character varying(254) NOT NULL UNIQUE,
	user_age smallint NOT NULL CHECK (user_age BETWEEN 0 AND 150),
	user_name character varying(70) NOT NULL,
	user_password character varying(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS public."Allergy" (
	allergy_allergen character varying(75) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS public."Recipe" (
	recipe_id bigserial NOT NULL PRIMARY KEY,
	recipe_name character varying(35) NOT NULL,
	recipe_instructions text NOT NULL,
	recipe_difficulty character varying(9) CHECK (recipe_difficulty IN ('easy', 'average', 'difficult')),
	recipe_time smallint CHECK (recipe_time > 0),
	recipe_servings smallint CHECK (recipe_servings > 0),
	recipe_cuisine character varying(35),
	recipe_dishType character varying(35),
	recipe_type character varying(7) NOT NULL CHECK (recipe_type IN ('system', 'user'))
);

CREATE TABLE IF NOT EXISTS public."SystemRecipe" (
	recipe_id bigint NOT NULL CHECK (recipe_id > 0) REFERENCES public."Recipe" (recipe_id),
	PRIMARY KEY (recipe_id)
);

CREATE TABLE IF NOT EXISTS public."UserRecipe" (
	recipe_id bigint NOT NULL CHECK (recipe_id > 0) REFERENCES public."Recipe" (recipe_id),
	user_username character varying(30) NOT NULL REFERENCES public."User" (user_username),
	PRIMARY KEY (recipe_id)
);

CREATE TABLE IF NOT EXISTS public."SharedRecipe" (
	recipe_id bigint NOT NULL CHECK (recipe_id > 0) REFERENCES public."UserRecipe" (recipe_id),
	user_username character varying(30) NOT NULL REFERENCES public."User" (user_username),
	PRIMARY KEY (recipe_id, user_username)
);

CREATE TABLE IF NOT EXISTS public."List" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."House" (house_id),
	list_id smallserial NOT NULL,
	list_name character varying(35) NOT NULL,
	list_type character varying(7) NOT NULL CHECK (list_type IN ('system', 'user')),
	PRIMARY KEY (house_id, list_id)
);

CREATE TABLE IF NOT EXISTS public."SystemList" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	list_id smallint NOT NULL CHECK (list_id > 0),
	PRIMARY KEY (house_id, list_id),
	FOREIGN KEY (house_id, list_id) REFERENCES public."List" (house_id, list_id)
);

CREATE TABLE IF NOT EXISTS public."UserList" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	list_id smallint NOT NULL CHECK (list_id > 0),
	user_username character varying(30) NOT NULL REFERENCES public."User" (user_username),
	list_shareable boolean,
	PRIMARY KEY (house_id, list_id),
	FOREIGN KEY (house_id, list_id) REFERENCES public."List" (house_id, list_id)
);

CREATE TABLE IF NOT EXISTS public."Category" (
	category_id serial NOT NULL PRIMARY KEY,
	category_name character varying(35) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public."Product" (
	category_id integer NOT NULL CHECK (category_id > 0) REFERENCES public."Category" (category_id),
	product_id serial NOT NULL,
	product_name character varying(35) NOT NULL,
	product_edible boolean NOT NULL,
	product_shelfLife smallint NOT NULL CHECK (product_shelfLife > 0),
	product_shelfLifeTimeUnit character varying(35) NOT NULL CHECK (product_shelfLifeTimeUnit IN ('day', 'week', 'month', 'year')),
	PRIMARY KEY (category_id, product_id)
);

CREATE TABLE IF NOT EXISTS public."StockItem" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."House" (house_id),
	stockItem_sku character varying(128) NOT NULL,
	category_id integer NOT NULL CHECK (category_id > 0),
	product_id integer NOT NULL CHECK (product_id > 0),
	stockItem_brand character varying(35) NOT NULL,
	stockItem_segment character varying(35) NOT NULL,
	stockItem_variety character varying(35) NOT NULL,
	stockItem_quantity smallint NOT NULL CHECK (stockItem_quantity > 0),
	stockItem_segmentUnit character varying(5) NOT NULL CHECK (stockItem_segmentUnit IN ('kg', 'dag', 'hg', 'g', 'dg', 'cg', 'mg', 'kl', 'hl', 'dal', 'l', 'dl', 'cl', 'ml', 'oz', 'lb', 'pt', 'fl oz', 'units')),
	stockItem_description text,
	stockItem_conservationStorage character varying(128) NOT NULL,
	PRIMARY KEY (house_id, stockItem_sku),
	UNIQUE (category_id, product_id, stockItem_brand, stockItem_segment, stockItem_variety),
	FOREIGN KEY (category_id, product_id) REFERENCES public."Product" (category_id, product_id)
);

CREATE TABLE IF NOT EXISTS public."Ingredient" (
	recipe_id integer NOT NULL CHECK (recipe_id > 0) REFERENCES public."Recipe" (recipe_id),
	category_id integer NOT NULL CHECK (category_id > 0),
	product_id integer NOT NULL CHECK (product_id > 0),
	ingredient_quantity integer NOT NULL CHECK (ingredient_quantity > 0),
	ingredient_quantityUnit character varying(5) NOT NULL CHECK (ingredient_quantityUnit IN ('kg', 'dag', 'hg', 'g', 'dg', 'cg', 'mg', 'kl', 'hl', 'dal', 'l', 'dl', 'cl', 'ml', 'oz', 'lb', 'pt', 'fl oz', 'units')),
	PRIMARY KEY (recipe_id, category_id, product_id),
	FOREIGN KEY (category_id, product_id) REFERENCES public."Product" (category_id, product_id)
);

CREATE TABLE IF NOT EXISTS public."Storage" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."House" (house_id),
	storage_id smallserial NOT NULL CHECK (storage_id > 0),
	storage_name character varying(35) NOT NULL,
	storage_temperature numrange NOT NULL,
	PRIMARY KEY (house_id, storage_id)
);

CREATE TABLE IF NOT EXISTS public."UserHouse" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."House" (house_id),
	user_username character varying(30) NOT NULL REFERENCES public."User" (user_username),
	userHouse_administrator boolean,
	PRIMARY KEY (house_id, user_username)
);

CREATE TABLE IF NOT EXISTS public."StockItemStorage" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockItem_sku character varying(128) NOT NULL,
	storage_id smallint NOT NULL CHECK (storage_id > 0),
	stockItemStorage_quantity smallint NOT NULL CHECK (stockItemStorage_quantity > 0),
	PRIMARY KEY (house_id, stockItem_sku, storage_id),
	FOREIGN KEY (house_id, stockItem_sku) REFERENCES public."StockItem" (house_id, stockItem_sku),
	FOREIGN KEY (house_id, storage_id) REFERENCES public."Storage" (house_id, storage_id)
);

CREATE TABLE IF NOT EXISTS public."StockItemMovement" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockItem_sku character varying(128) NOT NULL,
	storage_id smallint NOT NULL CHECK (storage_id > 0),
	stockItemMovement_type boolean NOT NULL,
	stockItemMovement_dateTime timestamp NOT NULL,
	stockItemMovement_quantity smallint NOT NULL CHECK (stockItemMovement_quantity > 0),
	PRIMARY KEY (house_id, stockItem_sku, stockItemMovement_type, stockItemMovement_dateTime, stockItemMovement_quantity),
	FOREIGN KEY (house_id, stockItem_sku) REFERENCES public."StockItem" (house_id, stockItem_sku),
	FOREIGN KEY (house_id, storage_id) REFERENCES public."Storage" (house_id, storage_id)
);

CREATE TABLE IF NOT EXISTS public."HouseAllergy" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."House" (house_id),
	allergy_allergen character varying(75) NOT NULL REFERENCES public."Allergy" (allergy_allergen),
	houseAllergy_alergicsNum smallint NOT NULL CHECK (houseAllergy_alergicsNum > 0),
	PRIMARY KEY (house_id, allergy_allergen)
);

CREATE TABLE IF NOT EXISTS public."ListProduct" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	list_id smallint NOT NULL CHECK (list_id > 0),
	category_id integer NOT NULL CHECK (category_id > 0),
	product_id integer NOT NULL CHECK (product_id > 0),
	listProduct_brand character varying(35),
	listProduct_quantity smallint NOT NULL CHECK (listProduct_quantity > 0),
	PRIMARY KEY (house_id, list_id, category_id, product_id),
	FOREIGN KEY (house_id, list_id) REFERENCES public."List" (house_id, list_id),
	FOREIGN KEY (category_id, product_id) REFERENCES public."Product" (category_id, product_id)
);

CREATE TABLE IF NOT EXISTS public."StockItemAllergy" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockItem_sku character varying(128) NOT NULL,
	allergy_allergen character varying(75) NOT NULL REFERENCES public."Allergy" (allergy_allergen),
	PRIMARY KEY (house_id, stockItem_sku, allergy_allergen),
	FOREIGN KEY (house_id, stockItem_sku) REFERENCES public."StockItem" (house_id, stockItem_sku)
);

CREATE TABLE IF NOT EXISTS public."Date" (
	date_date timestamp NOT NULL PRIMARY KEY -- Date (YYYY/MM/DD)
);

CREATE TABLE IF NOT EXISTS public."ExpirationDate" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockItem_sku character varying(128) NOT NULL,
	date_date timestamp NOT NULL REFERENCES public."Date" (date_date),
	PRIMARY KEY (house_id, stockItem_sku, date_date),
	FOREIGN KEY (house_id, stockItem_sku) REFERENCES public."StockItem" (house_id, stockItem_sku)
);