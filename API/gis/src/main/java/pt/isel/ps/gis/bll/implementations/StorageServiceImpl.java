package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
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
import java.util.Locale;

@Service
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;
    private final HouseRepository houseRepository;

    private final MessageSource messageSource;

    public StorageServiceImpl(StorageRepository storageRepository, HouseRepository houseRepository, MessageSource messageSource) {
        this.storageRepository = storageRepository;
        this.houseRepository = houseRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean existsStorageByStorageId(long houseId, short storageId) throws EntityException {
        return storageRepository.existsById(new StorageId(houseId, storageId));
    }

    @Override
    public Storage getStorageByStorageId(long houseId, short storageId, Locale locale) throws EntityException, EntityNotFoundException {
        return storageRepository
                .findById(new StorageId(houseId, storageId))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("storage_In_House_Not_Exist", null, locale)));
    }

    @Transactional
    @Override
    public List<Storage> getStorageByHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId, locale);
        return storageRepository.findAllById_HouseId(houseId);
    }

    @Transactional
    @Override
    public Storage addStorage(long houseId, String name, Float minimumTemperature, Float maximumTemperature, Locale locale) throws EntityException, EntityNotFoundException {
        Storage storage = new Storage(houseId, name, new Numrange(minimumTemperature, maximumTemperature));
        checkHouse(houseId, locale);
        return storageRepository.insertStorage(storage);
    }

    @Transactional
    @Override
    public Storage updateStorage(long houseId, short storageId, String name, Float minimumTemperature, Float maximumTemperature, Locale locale) throws EntityNotFoundException, EntityException {
        Storage storage = getStorageByStorageId(houseId, storageId, locale);
        storage.setStorageName(name);
        storage.setStorageTemperature(new Numrange(minimumTemperature, maximumTemperature));
        return storage;
    }

    @Transactional
    @Override
    public void deleteStorageByStorageId(long houseId, short storageId, Locale locale) throws EntityException, EntityNotFoundException {
        StorageId id = new StorageId(houseId, storageId);
        if (!storageRepository.existsById(id))
            throw new EntityNotFoundException(messageSource.getMessage("storage_Id_Not_Exist", new Object[]{ storageId, houseId}, locale));
        storageRepository.deleteCascadeStorageById(id);
    }

    private void checkHouse(long houseId, Locale locale) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(messageSource.getMessage("house_Not_Exist", null, locale));
    }
}
