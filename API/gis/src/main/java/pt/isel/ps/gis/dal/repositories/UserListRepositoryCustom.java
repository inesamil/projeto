package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.UserList;
import pt.isel.ps.gis.model.UserListId;

public interface UserListRepositoryCustom {

    /**
     * Insert user list.
     *
     * @param userList instance of UserList to be inserted.
     * @return UserList inserted and with id setted up.
     */
    UserList insertUserList(UserList userList);

    /**
     * Delete user list and all associated entities
     *
     * @param id The id of the user list
     */
    void deleteCascadeUserListById(UserListId id);
}
