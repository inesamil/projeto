package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Storage;

import java.util.List;

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
     * @param houseId   identificador da casa
     * @param storageId identificador do local de armazenamento
     * @return Optional<Storage>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Storage getStorageByStorageId(long houseId, short storageId) throws EntityException, EntityNotFoundException;

    /**
     * Listar todos os locais de armazenamento duma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<Storage>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<Storage> getStorageByHouseId(long houseId) throws EntityException, EntityNotFoundException;

    /**
     * Adicionar um local de armazenamento numa casa
     *
     * @param storage local de armazenamento a adicionar
     * @return Storage
     */
    Storage addStorage(long houseId, String name, Float minimumTemperature, Float maximumTemperature) throws EntityException, EntityNotFoundException;

    /**
     * Atualizar um local de armazenamento duma casa
     *
     * @param storage local de armazenamento atualizado
     * @return Storage
     * @throws EntityNotFoundException se o local de armazenamento especificado não existir na casa particularizada
     */
    Storage updateStorage(long houseId, short storageId, String name, Float minimumTemperature, Float maximumTemperature) throws EntityNotFoundException, EntityException;

    /**
     * Remover um local de armazenamento duma casa
     *
     * @param houseId   identificador da casa
     * @param storageId identifcador do local de armazenamento
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o local de armazenamento especificado não existir na casa particularizada
     */
    void deleteStorageByStorageId(long houseId, short storageId) throws EntityException, EntityNotFoundException;
}
