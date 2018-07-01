package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityAlreadyExistsException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Users;

import java.util.List;
import java.util.Locale;

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
     * Obter um utilizador através do ser ID de utilizador
     *
     * @param userId identificador do utilizador
     * @return User
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o utilizador com o ID especificado não existir
     */
    Users getUserByUserId(Long userId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Obter um utilizador através do seu nome de utilizador
     *
     * @param username identificador do utilizador
     * @return User
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o utilizador com o ID especificado não existir
     */
    Users getUserByUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Obter os utilizadores cujo nome de utilizador começe por username
     *
     * @param username identificador do utilizador
     * @return List<Users>
     */
    List<Users> getUsersStartsWithUsername(String username) throws EntityException;

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
    Users addUser(String username, String email, Short age, String name, String password, Locale locale) throws EntityException, EntityAlreadyExistsException;

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
    Users updateUser(String username, String email, Short age, String name, String password, Locale locale) throws EntityException, EntityNotFoundException, EntityAlreadyExistsException;

    /**
     * Remover um utilizador
     *
     * @param username identificador do utilizador
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o utilizador especificado não existir
     */
    void deleteUserByUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException;
}
