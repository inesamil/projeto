package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.model.Users;

public interface UserService {

    /**
     * Obter um utilizador através do ser nome de utilizador
     *
     * @param username identificador do utilizador
     * @return User
     */
    Users getUserByUserId(String username);

    /**
     * Adicionar um utilizador
     *
     * @param user utilizador a adicionar
     * @return User
     */
    Users addUser(Users user);

    /**
     * Atualizar um utilizador
     *
     * @param user utilizador a atualizar
     * @return User
     */
    Users updateUser(Users user);

    /**
     * Remover um utilizador
     *
     * @param username identificador do utilizador
     */
    void deleteUser(String username);
}
