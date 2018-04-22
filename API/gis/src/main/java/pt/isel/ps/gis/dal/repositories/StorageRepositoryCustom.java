package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;

public interface StorageRepositoryCustom {

    Storage insertStorage(Storage storage);

    void deleteCascadeStorageById(StorageId id);
}
