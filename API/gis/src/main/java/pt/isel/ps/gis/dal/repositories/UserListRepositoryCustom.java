package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.UserList;
import pt.isel.ps.gis.model.UserListId;

public interface UserListRepositoryCustom {

    /**
     * Insert user list.
     *
     * @param houseId   The id of the house
     * @param listName  The name of the list
     * @param username  The username of the user
     * @param shareable True means that this list is shareable, false is not shareable
     * @return UserList inserted and with id setted up.
     */
    UserList insertUserList(Long houseId, String listName, String username, Boolean shareable);

    /**
     * Delete user list and all associated entities
     *
     * @param id The id of the user list
     */
    void deleteCascadeUserListById(UserListId id);
}
