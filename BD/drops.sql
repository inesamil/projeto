-- DROP functions
DROP FUNCTION IF EXISTS delete_user;
DROP FUNCTION IF EXISTS delete_house;
DROP FUNCTION IF EXISTS delete_user_list;
DROP FUNCTION IF EXISTS delete_stock_item;
DROP FUNCTION IF EXISTS delete_storage;
DROP FUNCTION IF EXISTS insert_user_list;
DROP FUNCTION IF EXISTS insert_system_list;
DROP FUNCTION IF EXISTS insert_product;
DROP FUNCTION IF EXISTS insert_stock_item;
DROP FUNCTION IF EXISTS generate_sku;
DROP FUNCTION IF EXISTS insert_storage;
DROP FUNCTION IF EXISTS get_lists_filtered;
DROP FUNCTION IF EXISTS get_stock_items_filtered;
DROP FUNCTION IF EXISTS get_movements_filtered;

-- DROP tables
DROP TABLE IF EXISTS public."expirationdate";
DROP TABLE IF EXISTS public."date";
DROP TABLE IF EXISTS public."stockitemallergy";
DROP TABLE IF EXISTS public."listproduct";
DROP TABLE IF EXISTS public."houseallergy";
DROP TABLE IF EXISTS public."stockitemmovement";
DROP TABLE IF EXISTS public."stockitemstorage";
DROP TABLE IF EXISTS public."userhouse";
DROP TABLE IF EXISTS public."storage";
DROP TABLE IF EXISTS public."ingredient";
DROP TABLE IF EXISTS public."stockitem";
DROP TABLE IF EXISTS public."product";
DROP TABLE IF EXISTS public."category";
DROP TABLE IF EXISTS public."userlist";
DROP TABLE IF EXISTS public."systemlist";
DROP TABLE IF EXISTS public."list";
DROP TABLE IF EXISTS public."sharedrecipe";
DROP TABLE IF EXISTS public."userrecipe";
DROP TABLE IF EXISTS public."systemrecipe";
DROP TABLE IF EXISTS public."recipe";
DROP TABLE IF EXISTS public."allergy";
DROP TABLE IF EXISTS public."users";
DROP TABLE IF EXISTS public."house";

-- DROP schema
DROP SCHEMA IF EXISTS public;