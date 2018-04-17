package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.StorageService;
import pt.isel.ps.gis.dal.repositories.StorageRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;

import java.util.Optional;

public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;

    public StorageServiceImpl(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Override
    public boolean existsStorageByStorageId(StorageId storageId) {
        return storageRepository.existsById(storageId);
    }

    @Override
    public Optional<Storage> getStorageByStorageId(StorageId storageId) {
        return storageRepository.findById(storageId);
    }

    @Override
    public Iterable<Storage> getStorageByHouseId(Long houseId) {
        return storageRepository.findAllById_HouseId(houseId);
    }

    @Override
    public Storage addStorage(Storage storage) throws EntityException {
        Storage newStorage = storage;
        if (storage.getId() != null) {
            // É preciso garantir que storageId está a NULL, para ser feita inserção do novo local de armazenamento.
            // Caso contrário, poderia ser atualizado um local de armazenamento já existente.
            newStorage = new Storage(storage.getStorageName(), storage.getStorageTemperature());
        }
        return storageRepository.save(newStorage);
    }

    @Override
    public Storage updateStorage(Storage storage) throws EntityNotFoundException {
        if (!existsStorageByStorageId(storage.getId()))
            throw new EntityNotFoundException(String.format("Storage with ID {%d, %d} does not exist.",
                    storage.getId().getHouseId(), storage.getId().getStorageId()));
        return storageRepository.save(storage);
    }

    @Override
    public void deleteStorage(StorageId storageId) throws EntityNotFoundException {
        if (!existsStorageByStorageId(storageId))
            throw new EntityNotFoundException(String.format("Storage with ID {%d, %d} does not exist.",
                    storageId.getHouseId(), storageId.getStorageId()));
        storageRepository.deleteById(storageId);
    }
}
