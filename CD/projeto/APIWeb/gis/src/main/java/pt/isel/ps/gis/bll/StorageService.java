package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.Storage;

import java.util.List;
import java.util.Locale;

public interface StorageService {

    /**
     * Verifica se um dado local de armazenamento existe através do seu ID
     *
     * @param houseId   identificador da casa
     * @param storageId identificador do local de armazenamento
     * @return true se o local de armazenamento existir, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsStorageByStorageId(long houseId, short storageId) throws EntityException;

    /**
     * Obter um local de armazenamento através do seu ID
     *
     * @param username identificador do utilizador
     * @param houseId   identificador da casa
     * @param storageId identificador do local de armazenamento
     * @return Optional<Storage>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Storage getStorageByStorageId(String username, long houseId, short storageId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Listar todos os locais de armazenamento duma casa através do ID da casa
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     * @return List<Storage>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<Storage> getStorageByHouseId(String username, long houseId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Adicionar um local de armazenamento numa casa
     *
     * @return Storage
     */
    Storage addStorage(String username, long houseId, String name, Float minimumTemperature, Float maximumTemperature, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Atualizar um local de armazenamento duma casa
     *
     * @return Storage
     * @throws EntityNotFoundException se o local de armazenamento especificado não existir na casa particularizada
     */
    Storage updateStorage(String username, long houseId, short storageId, String name, Float minimumTemperature, Float maximumTemperature, Locale locale) throws EntityNotFoundException, EntityException, InsufficientPrivilegesException;

    /**
     * Remover um local de armazenamento duma casa
     *
     *
     * @param username
     * @param houseId   identificador da casa
     * @param storageId identifcador do local de armazenamento
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o local de armazenamento especificado não existir na casa particularizada
     */
    void deleteStorageByStorageId(String username, long houseId, short storageId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;
}
