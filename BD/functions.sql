 
 -- Procedure to delete a User
 -- DROP FUNCTION delete_user
CREATE OR REPLACE FUNCTION delete_user(username character varying(35)) 
RETURNS VOID AS $$
DECLARE
    cursor_userLists CURSOR FOR SELECT * FROM public."UserList" WHERE user_username = username;
	rec_userList   RECORD;
BEGIN
	--BEGIN
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
	--COMMIT;
END;
$$ LANGUAGE plpgsql;

 -- Procedure to delete a House
 -- DROP FUNCTION delete_house
CREATE OR REPLACE FUNCTION delete_house(id bigint) 
RETURNS VOID AS $$
BEGIN
	--BEGIN
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
	--COMMIT;
END;
$$ LANGUAGE plpgsql;


 -- Procedure to delete a UserList
 -- DROP FUNCTION delete_user_list
CREATE OR REPLACE FUNCTION delete_user_list(houseID bigint, listID smallint) 
RETURNS VOID AS $$
BEGIN
	--BEGIN
		-- Remove UserList
		DELETE FROM public."UserList" WHERE house_id = houseID AND list_id = listID;
		
		-- Remove ListProduct 
		DELETE FROM public."ListProduct" WHERE house_id = houseID AND list_id = listID;
		
		-- Remove List
		DELETE FROM public."List" WHERE house_id = houseID AND list_id = listID;
	--COMMIT;
END;
$$ LANGUAGE plpgsql;


---TESTS---

SELECT delete_user('ze')
SELECT delete_house(1) 


INSERT INTO public."House" VALUES (1, 'house', 0, 0, 1, 0);
INSERT INTO public."User" VALUES ('ze', 'email', 20, 'ze', '124');
INSERT INTO public."UserHouse" VALUES (1, 'ze', true);
INSERT INTO public."List" VALUES (1, 1, 'list', 'user');
INSERT INTO public."UserList" VALUES (1, 1, 'ze', true);
--SELECT * FROM public."House" 
--SELECT * FROM public."User"
--SELECT * FROM public."UserHouse"
--SELECT * FROM public."List"
--SELECT * FROM public."UserList"
