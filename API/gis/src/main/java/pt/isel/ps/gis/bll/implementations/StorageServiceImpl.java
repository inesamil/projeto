package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.StorageService;
import pt.isel.ps.gis.dal.repositories.StorageRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;

    public StorageServiceImpl(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Override
    public boolean existsStorageByStorageId(long houseId, short storageId) throws EntityException {
        return storageRepository.existsById(new StorageId(houseId, storageId));
    }

    @Override
    public Optional<Storage> getStorageByStorageId(StorageId storageId) {
        return storageRepository.findById(storageId);
    }

    @Override
    public List<Storage> getStorageByHouseId(long houseId) {
        //TODO return storageRepository.findAllById_HouseId(houseId);
        throw new NotImplementedException();
    }

    @Override
    public Storage addStorage(Storage storage){
        return storageRepository.insertStorage(storage);
    }

    @Override
    public Storage updateStorage(Storage storage) throws EntityNotFoundException {
        StorageId id = storage.getId();
        if (!storageRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Storage with ID %d does not exist in the house with ID %d.",
                    id.getStorageId(),id.getHouseId()));
        return storageRepository.save(storage);
    }

    @Override
    public void deleteStorage(long houseId, short storageId) throws EntityException, EntityNotFoundException {
        StorageId id = new StorageId(houseId, storageId);
        if (!storageRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Storage with ID %d does not exist in the house with ID %d.",
                    storageId, houseId));
        storageRepository.deleteById(id);
    }
}
