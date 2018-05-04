package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.StorageService;
import pt.isel.ps.gis.dal.repositories.StorageRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Optional;

@Service
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
    public Optional<Storage> getStorageByStorageId(long houseId, short storageId) throws EntityException {
        return storageRepository.findById(new StorageId(houseId, storageId));
    }

    @Override
    public List<Storage> getStorageByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return storageRepository.findAllById_HouseId(houseId);
    }

    @Override
    public Storage addStorage(Storage storage) throws EntityException {
        if (storage.getId() != null && storageRepository.existsById(storage.getId()))
            throw new EntityException(String.format("Storage with ID %d in the house with ID %d already exists.",
                    storage.getId().getStorageId(),storage.getId().getHouseId()));
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
    public void deleteStorageByStorageId(long houseId, short storageId) throws EntityException, EntityNotFoundException {
        StorageId id = new StorageId(houseId, storageId);
        if (!storageRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Storage with ID %d does not exist in the house with ID %d.",
                    storageId, houseId));
        storageRepository.deleteCascadeStorageById(id);
    }
}
