CREATE SCHEMA IF NOT EXISTS public AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS public."house" (
	house_id bigserial NOT NULL PRIMARY KEY,
	house_name character varying(35) NOT NULL,
	house_characteristics json NOT NULL
	--house_babiesNumber smallint NOT NULL CHECK (house_babiesNumber BETWEEN 0 AND 100),
	--house_childrenNumber smallint NOT NULL CHECK (house_childrenNumber BETWEEN 0 AND 100),
	--house_adultsNumber smallint NOT NULL CHECK (house_adultsNumber BETWEEN 0 AND 100),
	--house_seniorsNumber smallint NOT NULL CHECK (house_seniorsNumber BETWEEN 0 AND 100)
);

CREATE TABLE IF NOT EXISTS public."users" (
	users_id bigserial NOT NULL PRIMARY KEY,
	users_username character varying(30) NOT NULL UNIQUE,
	users_email character varying(254) NOT NULL UNIQUE,
	users_age smallint NOT NULL CHECK (users_age BETWEEN 0 AND 150),
	users_name character varying(70) NOT NULL,
	users_password character varying(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS public."role" (
	role_id smallserial NOT NULL PRIMARY KEY,
	role_name character varying(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public."usersrole" (
	users_id bigint NOT NULL CHECK (users_id > 0) REFERENCES public."users" (users_id),
	role_id smallint NOT NULL CHECK (role_id > 0) REFERENCES public."role" (role_id),
	PRIMARY KEY (users_id, role_id)
);

CREATE TABLE IF NOT EXISTS public."allergy" (
	allergy_allergen character varying(75) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS public."list" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."house" (house_id),
	list_id smallint NOT NULL,
	list_name character varying(35) NOT NULL,
	list_type character varying(7) NOT NULL CHECK (list_type IN ('system', 'user')),
	PRIMARY KEY (house_id, list_id)
);

CREATE TABLE IF NOT EXISTS public."systemlist" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	list_id smallint NOT NULL CHECK (list_id > 0),
	PRIMARY KEY (house_id, list_id),
	FOREIGN KEY (house_id, list_id) REFERENCES public."list" (house_id, list_id)
);

CREATE TABLE IF NOT EXISTS public."userlist" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	list_id smallint NOT NULL CHECK (list_id > 0),
	users_id bigint NOT NULL CHECK (users_id > 0) REFERENCES public."users" (users_id),
	list_shareable boolean,
	PRIMARY KEY (house_id, list_id),
	FOREIGN KEY (house_id, list_id) REFERENCES public."list" (house_id, list_id)
);

CREATE TABLE IF NOT EXISTS public."category" (
	category_id serial NOT NULL PRIMARY KEY,
	category_name character varying(35) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public."product" (
	category_id integer NOT NULL CHECK (category_id > 0) REFERENCES public."category" (category_id),
	product_id serial NOT NULL,
	product_name character varying(35) NOT NULL UNIQUE,
	product_edible boolean NOT NULL,
	product_shelflife smallint NOT NULL CHECK (product_shelfLife > 0),
	product_shelflifetimeunit character varying(5) NOT NULL CHECK (product_shelflifetimeunit IN ('day', 'week', 'month', 'year')),
	PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS public."stockitem" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."house" (house_id),
	stockitem_sku character varying(128) NOT NULL,
	product_id integer NOT NULL CHECK (product_id > 0),
	stockitem_brand character varying(35) NOT NULL,
	stockitem_segment real NOT NULL,
	stockitem_variety character varying(35) NOT NULL,
	stockitem_quantity smallint NOT NULL CHECK (stockitem_quantity >= 0),
	stockitem_segmentunit character varying(5) NOT NULL CHECK (stockitem_segmentunit IN ('kg', 'dag', 'hg', 'g', 'dg', 'cg', 'mg', 'kl', 'hl', 'dal', 'l', 'dl', 'cl', 'ml', 'oz', 'lb', 'pt', 'fl oz', 'units')),
	stockitem_description text,
	stockitem_conservationstorage character varying(128),
	PRIMARY KEY (house_id, stockitem_sku),
	UNIQUE (house_id, product_id, stockitem_brand, stockitem_segment, stockitem_variety),
	FOREIGN KEY (product_id) REFERENCES public."product" (product_id)
);

CREATE TABLE IF NOT EXISTS public."storage" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."house" (house_id),
	storage_id smallint NOT NULL CHECK (storage_id > 0),
	storage_name character varying(35) NOT NULL,
	storage_temperature numrange NOT NULL,
	PRIMARY KEY (house_id, storage_id)
);

CREATE TABLE IF NOT EXISTS public."userhouse" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."house" (house_id),
	users_id bigint NOT NULL CHECK (users_id > 0) REFERENCES public."users" (users_id),
	userhouse_administrator boolean,
	PRIMARY KEY (house_id, users_id)
);

CREATE TABLE IF NOT EXISTS public."stockitemstorage" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockitem_sku character varying(128) NOT NULL,
	storage_id smallint NOT NULL CHECK (storage_id > 0),
	stockitemstorage_quantity smallint NOT NULL CHECK (stockitemstorage_quantity >= 0),
	PRIMARY KEY (house_id, stockitem_sku, storage_id),
	FOREIGN KEY (house_id, stockitem_sku) REFERENCES public."stockitem" (house_id, stockitem_sku),
	FOREIGN KEY (house_id, storage_id) REFERENCES public."storage" (house_id, storage_id)
);

CREATE TABLE IF NOT EXISTS public."stockitemmovement" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockitem_sku character varying(128) NOT NULL,
	storage_id smallint NOT NULL CHECK (storage_id > 0),
	stockitemmovement_type boolean NOT NULL,
	stockitemmovement_datetime timestamp NOT NULL,
	stockitemmovement_quantity smallint NOT NULL CHECK (stockitemmovement_quantity > 0),
	stockitemmovement_finalquantity smallint NOT NULL CHECK (stockitemmovement_finalquantity >= 0),
	PRIMARY KEY (house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime),
	FOREIGN KEY (house_id, stockitem_sku) REFERENCES public."stockitem" (house_id, stockitem_sku),
	FOREIGN KEY (house_id, storage_id) REFERENCES public."storage" (house_id, storage_id)
);

CREATE TABLE IF NOT EXISTS public."houseallergy" (
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."house" (house_id),
	allergy_allergen character varying(75) NOT NULL REFERENCES public."allergy" (allergy_allergen),
	houseallergy_allergicsnum smallint NOT NULL CHECK (houseallergy_allergicsnum > 0),
	PRIMARY KEY (house_id, allergy_allergen)
);

CREATE TABLE IF NOT EXISTS public."listproduct" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	list_id smallint NOT NULL CHECK (list_id > 0),
	product_id integer NOT NULL CHECK (product_id > 0),
	listproduct_brand character varying(35),
	listproduct_quantity smallint NOT NULL CHECK (listproduct_quantity > 0),
	PRIMARY KEY (house_id, list_id, product_id),
	FOREIGN KEY (house_id, list_id) REFERENCES public."list" (house_id, list_id),
	FOREIGN KEY (product_id) REFERENCES public."product" (product_id)
);

CREATE TABLE IF NOT EXISTS public."stockitemallergy" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockitem_sku character varying(128) NOT NULL,
	allergy_allergen character varying(75) NOT NULL REFERENCES public."allergy" (allergy_allergen),
	PRIMARY KEY (house_id, stockitem_sku, allergy_allergen),
	FOREIGN KEY (house_id, stockitem_sku) REFERENCES public."stockitem" (house_id, stockitem_sku)
);

CREATE TABLE IF NOT EXISTS public."date" (
	date_date date NOT NULL PRIMARY KEY -- Date (YYYY/MM/DD)
);

CREATE TABLE IF NOT EXISTS public."expirationdate" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockitem_sku character varying(128) NOT NULL,
	date_date date NOT NULL REFERENCES public."date" (date_date),
	date_quantity smallint NOT NULL CHECK (date_quantity >= 0),
	PRIMARY KEY (house_id, stockitem_sku, date_date),
	FOREIGN KEY (house_id, stockitem_sku) REFERENCES public."stockitem" (house_id, stockitem_sku)
);

CREATE TABLE IF NOT EXISTS public."invitation" (
	users_id bigint NOT NULL CHECK (users_id > 0) REFERENCES public."users" (users_id),
	house_id bigint NOT NULL CHECK (house_id > 0) REFERENCES public."house" (house_id),
	PRIMARY KEY (house_id, users_id)
);

CREATE TABLE IF NOT EXISTS public."dailyquantity" (
	house_id bigint NOT NULL CHECK (house_id > 0),
	stockitem_sku character varying(128) NOT NULL,
	dailyquantity_date date NOT NULL,
	dailyquantity_quantity smallint NOT NULL CHECK (dailyquantity_quantity >= 0),
	PRIMARY KEY (house_id, stockitem_sku, dailyquantity_date),
	FOREIGN KEY (house_id, stockitem_sku) REFERENCES public."stockitem" (house_id, stockitem_sku)
);
