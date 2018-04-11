package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.models.Storage;

import java.util.List;

public interface StorageService {

    /**
     * Obter um local de armazenamento através do seu ID
     *
     * @param houseId   identificador da casa
     * @param storageId identificador do local de armazenamento
     * @return Storage
     */
    Storage getStorageByStorageId(Long houseId, Short storageId);

    /**
     * Listar todos os locais de armazenamento duma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<Storage>
     */
    List<Storage> getStorageByHouseId(Long houseId);

    /**
     * Adicionar um local de armazenamento numa casa
     *
     * @param storage local de armazenamento a adicionar
     * @return Storage
     */
    Storage addStorage(Storage storage);

    /**
     * Atualizar um local de armazenamento duma casa
     *
     * @param storage local de armazenamento atualizado
     * @return Storage
     */
    Storage updateStorage(Storage storage);

    /**
     * Remover um local de armazenamento duma casa
     *
     * @param houseId   identificador da casa
     * @param storageId identifcador do local de armazenamento
     */
    void deleteStorage(Long houseId, Short storageId);
}
