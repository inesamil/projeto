package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Users;

import java.util.Optional;

public interface UserService {

    /**
     * Verifica se um dado utilizador existe através do seu ID
     *
     * @param username identificador do utilizador
     * @return true se a casa existir, false caso contrário
     */
    boolean existsUserByUserId(String username);

    /**
     * Obter um utilizador através do ser nome de utilizador
     *
     * @param username identificador do utilizador
     * @return User
     */
    Optional<Users> getUserByUserId(String username);

    /**
     * Adicionar um utilizador
     *
     * @param user utilizador a adicionar
     * @return User
     */
    Users addUser(Users user) throws EntityException;

    /**
     * Atualizar um utilizador
     *
     * @param user utilizador a atualizar
     * @return User
     */
    Users updateUser(Users user) throws EntityNotFoundException;

    /**
     * Remover um utilizador
     *
     * @param username identificador do utilizador
     */
    void deleteUser(String username) throws EntityNotFoundException;
}
