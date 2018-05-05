package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;

public interface StorageRepositoryCustom {

    /**
     * Insert storage
     *
     * @param storage instance of Storage to be inserted.
     * @return The storage inserted with id setted up
     */
    Storage insertStorage(Storage storage);

    /**
     * Delete specific storage and all associated entities
     *
     * @param id The id of the storage
     */
    void deleteCascadeStorageById(StorageId id);
}
