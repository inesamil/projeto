package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;

import java.util.List;
import java.util.Optional;

public interface StorageService {

    /**
     * Verifica se um dado local de armazenamento existe através do seu ID
     *
     * @param storageId identificador do local de armazenamento
     * @return true se o local de armazenamento existir, false caso contrário
     */
    boolean existsStorageByStorageId(StorageId storageId);

    /**
     * Obter um local de armazenamento através do seu ID
     *
     * @param storageId identificador do local de armazenamento
     * @return Storage
     */
    Optional<Storage> getStorageByStorageId(StorageId storageId);

    /**
     * Listar todos os locais de armazenamento duma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<Storage>
     */
    List<Storage> getStorageByHouseId(long houseId);

    /**
     * Adicionar um local de armazenamento numa casa
     *
     * @param storage local de armazenamento a adicionar
     * @return Storage
     */
    Storage addStorage(Storage storage) throws EntityException;

    /**
     * Atualizar um local de armazenamento duma casa
     *
     * @param storage local de armazenamento atualizado
     * @return Storage
     */
    Storage updateStorage(Storage storage) throws EntityNotFoundException;

    /**
     * Remover um local de armazenamento duma casa
     *
     * @param houseId identificador da casa
     * @param storageId identifcador do local de armazenamento
     */
    void deleteStorage(long houseId, short storageId) throws EntityException, EntityNotFoundException;
}
