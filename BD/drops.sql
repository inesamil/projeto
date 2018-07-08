-- DROP functions
DROP FUNCTION IF EXISTS delete_user;
DROP FUNCTION IF EXISTS delete_house;
DROP FUNCTION IF EXISTS delete_user_list;
DROP FUNCTION IF EXISTS delete_stock_item;
DROP FUNCTION IF EXISTS delete_storage;
DROP FUNCTION IF EXISTS insert_user_list;
DROP FUNCTION IF EXISTS insert_system_list;
DROP FUNCTION IF EXISTS insert_movement;
DROP FUNCTION IF EXISTS generate_sku;
DROP FUNCTION IF EXISTS insert_storage;

-- DROP tables
DROP TABLE IF EXISTS public."dailyquantity";
DROP TABLE IF EXISTS public."invitation";
DROP TABLE IF EXISTS public."expirationdate";
DROP TABLE IF EXISTS public."date";
DROP TABLE IF EXISTS public."stockitemallergy";
DROP TABLE IF EXISTS public."listproduct";
DROP TABLE IF EXISTS public."houseallergy";
DROP TABLE IF EXISTS public."stockitemmovement";
DROP TABLE IF EXISTS public."stockitemstorage";
DROP TABLE IF EXISTS public."userhouse";
DROP TABLE IF EXISTS public."storage";
DROP TABLE IF EXISTS public."stockitem";
DROP TABLE IF EXISTS public."product";
DROP TABLE IF EXISTS public."category";
DROP TABLE IF EXISTS public."userlist";
DROP TABLE IF EXISTS public."systemlist";
DROP TABLE IF EXISTS public."list";
DROP TABLE IF EXISTS public."allergy";
DROP TABLE IF EXISTS public."usersrole";
DROP TABLE IF EXISTS public."role";
DROP TABLE IF EXISTS public."users";
DROP TABLE IF EXISTS public."house";

-- DROP schema
DROP SCHEMA IF EXISTS public;