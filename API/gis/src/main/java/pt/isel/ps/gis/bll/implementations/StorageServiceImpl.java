package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StorageService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.StorageRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Numrange;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";
    private static final String STORAGE_NOT_EXIST = "Storage does not exist in this house.";

    private final StorageRepository storageRepository;
    private final HouseRepository houseRepository;

    public StorageServiceImpl(StorageRepository storageRepository, HouseRepository houseRepository) {
        this.storageRepository = storageRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public boolean existsStorageByStorageId(long houseId, short storageId) throws EntityException {
        return storageRepository.existsById(new StorageId(houseId, storageId));
    }

    @Override
    public Storage getStorageByStorageId(long houseId, short storageId) throws EntityException, EntityNotFoundException {
        return storageRepository
                .findById(new StorageId(houseId, storageId))
                .orElseThrow(() -> new EntityNotFoundException(STORAGE_NOT_EXIST));
    }

    @Override
    public List<Storage> getStorageByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return storageRepository.findAllById_HouseId(houseId);
    }

    @Override
    public Storage addStorage(long houseId, String name, Float minimumTemperature, Float maximumTemperature) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        Storage storage = new Storage(houseId, name, new Numrange(minimumTemperature, maximumTemperature));
        checkHouse(houseId);
        return storageRepository.insertStorage(storage);
    }

    @Transactional
    @Override
    public Storage updateStorage(long houseId, short storageId, String name, Float minimumTemperature, Float maximumTemperature) throws EntityNotFoundException, EntityException {
        Storage storage = getStorageByStorageId(houseId, storageId);
        storage.setStorageName(name);
        storage.setStorageTemperature(new Numrange(minimumTemperature, maximumTemperature));
        return storage;
    }

    @Override
    public void deleteStorageByStorageId(long houseId, short storageId) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        StorageId id = new StorageId(houseId, storageId);
        if (!storageRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Storage with ID %d does not exist in the house with ID %d.",
                    storageId, houseId));
        storageRepository.deleteCascadeStorageById(id);
    }

    private void checkHouse(long houseId) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(HOUSE_NOT_EXIST);
    }
}
