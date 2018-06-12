package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Users;

public interface UserService {

    /**
     * Verifica se um dado utilizador existe através do seu ID
     *
     * @param username identificador do utilizador
     * @return true se a casa existir, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsUserByUserUsername(String username) throws EntityException;

    /**
     * Obter um utilizador através do ser nome de utilizador
     *
     * @param username identificador do utilizador
     * @return User
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Users getUserByUserUsername(String username) throws EntityException, EntityNotFoundException;

    /**
     * Adicionar um utilizador
     *
     * @param username identificador do utilizador
     * @param email    email do utilizador
     * @param age      idade do utilizador
     * @param name     nome do utilizador
     * @param password password do utilizador
     * @return User
     * @throws EntityException se os atributos especificados no parâmetro user forem inválidos
     */
    Users addUser(String username, String email, Short age, String name, String password) throws EntityException;

    /**
     * Atualizar um utilizador
     *
     * @param username identificador do utilizador
     * @param email    email do utilizador
     * @param age      idade do utilizador
     * @param name     nome do utilizador
     * @param password password do utilizador
     * @return User
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o utilizador especificado não existir
     */
    Users updateUser(String username, String email, Short age, String name, String password) throws EntityException, EntityNotFoundException;

    /**
     * Remover um utilizador
     *
     * @param username identificador do utilizador
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o utilizador especificado não existir
     */
    void deleteUserByUserUsername(String username) throws EntityException, EntityNotFoundException;
}
