package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StorageService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.StorageRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.Numrange;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;
import pt.isel.ps.gis.utils.AuthorizationProvider;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;

@Service
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;
    private final HouseRepository houseRepository;

    private final MessageSource messageSource;

    private final AuthorizationProvider authorizationProvider;

    public StorageServiceImpl(StorageRepository storageRepository, HouseRepository houseRepository, MessageSource messageSource, AuthorizationProvider authorizationProvider) {
        this.storageRepository = storageRepository;
        this.houseRepository = houseRepository;
        this.messageSource = messageSource;
        this.authorizationProvider = authorizationProvider;
    }

    @Override
    public boolean existsStorageByStorageId(long houseId, short storageId) throws EntityException {
        return storageRepository.existsById(new StorageId(houseId, storageId));
    }

    @Override
    public Storage getStorageByStorageId(String username, long houseId, short storageId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouse(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        return storageRepository
                .findById(new StorageId(houseId, storageId))
                .orElseThrow(() -> new EntityNotFoundException("Storage does not exist in this house.", messageSource.getMessage("storage_In_House_Not_Exist", null, locale)));
    }

    @Transactional
    @Override
    public List<Storage> getStorageByHouseId(String username, long houseId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouse(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        return storageRepository.findAllById_HouseId(houseId);
    }

    @Transactional
    @Override
    public Storage addStorage(String username, long houseId, String name, Float minimumTemperature, Float maximumTemperature, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouse(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        Storage storage = new Storage(houseId, name, new Numrange(minimumTemperature, maximumTemperature));
        return storageRepository.insertStorage(storage);
    }

    @Transactional
    @Override
    public Storage updateStorage(String username, long houseId, short storageId, String name, Float minimumTemperature, Float maximumTemperature, Locale locale) throws EntityNotFoundException, EntityException, InsufficientPrivilegesException {
        checkHouse(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        Storage storage = storageRepository
                .findById(new StorageId(houseId, storageId))
                .orElseThrow(() -> new EntityNotFoundException("Storage does not exist in this house.", messageSource.getMessage("storage_In_House_Not_Exist", null, locale)));
        storage.setStorageName(name);
        storage.setStorageTemperature(new Numrange(minimumTemperature, maximumTemperature));
        return storage;
    }

    @Transactional
    @Override
    public void deleteStorageByStorageId(long houseId, short storageId, Locale locale) throws EntityException, EntityNotFoundException {
        StorageId id = new StorageId(houseId, storageId);
        if (!storageRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Storage with ID %d does not exist in the house with ID %d.", storageId, houseId), messageSource.getMessage("storage_Id_Not_Exist", new Object[]{storageId, houseId}, locale));
        storageRepository.deleteCascadeStorageById(id);
    }

    private void checkHouse(long houseId, Locale locale) throws EntityNotFoundException, EntityException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale));
    }
}
