package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.dal.bdModel.User;

public interface UserService {

    /**
     * Obter um utilizador através do ser nome de utilizador
     * @param username identificador do utilizador
     * @return User
     */
    User getUserByUserId(String username);

    /**
     * Adicionar um utilizador
     * @param user utilizador a adicionar
     * @return User
     */
    User addUser(User user);

    /**
     * Atualizar um utilizador
     * @param user utilizador a atualizar
     * @return User
     */
    User updateUser(User user);

    /**
     * Remover um utilizador
     * @param user utilizador a remover
     */
    void deleteUser(User user);
}
