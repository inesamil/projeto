package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.dal.repositories.*;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.*;
import pt.isel.ps.gis.utils.AuthorizationProvider;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final UsersRepository usersRepository;
    private final UserHouseRepository userHouseRepository;
    private final SystemListRepository systemListRepository;
    //TODO: remove this before deployment
    private final StorageRepository storageRepository;

    private final MessageSource messageSource;

    private final AuthorizationProvider authorizationProvider;

    public HouseServiceImpl(HouseRepository houseRepository, UsersRepository usersRepository, UserHouseRepository userHouseRepository, SystemListRepository systemListRepository, StorageRepository storageRepository, MessageSource messageSource, AuthorizationProvider authorizationProvider) {
        this.houseRepository = houseRepository;
        this.usersRepository = usersRepository;
        this.userHouseRepository = userHouseRepository;
        this.systemListRepository = systemListRepository;
        this.storageRepository = storageRepository;
        this.messageSource = messageSource;
        this.authorizationProvider = authorizationProvider;
    }

    @Override
    public boolean existsHouseByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return houseRepository.existsById(houseId);
    }

    @Override
    public House getHouseByHouseId(String username, long houseId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        ValidationsUtils.validateHouseId(houseId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale)));
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        return house;
    }

    @Transactional
    @Override
    public List<House> getHousesByUserUsername(String authenticatedUsername, String username, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        ValidationsUtils.validateUserUsername(username);
        authorizationProvider.checkUserAuthorizationToAccessResource(authenticatedUsername, username);
        if (!usersRepository.existsByUsersUsername(username))
            throw new EntityNotFoundException(String.format("Username %s does not exist.", username), messageSource.getMessage("user_Username_Not_Exist", new String[]{username}, locale));
        return houseRepository.findAllByUsersUsername(username);
    }

    @Transactional
    @Override
    public House addHouse(String username, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        Characteristics characteristics = new Characteristics(
                babiesNumber,
                childrenNumber,
                adultsNumber,
                seniorsNumber
        );
        House house = new House(name, characteristics);
        Users user = usersRepository.findByUsersUsername(username).orElseThrow(() -> new EntityNotFoundException("User does not exist.", messageSource.getMessage("user_Not_Exist", null, locale)));
        house = houseRepository.save(house);
        UserHouse userHouse = userHouseRepository.save(new UserHouse(house.getHouseId(), user.getUsersId(), true));
        userHouse.setUsersByUsersId(user);
        house.setUserhousesByHouseId(new ArrayList<>(Collections.singleton(userHouse)));
        //Create SystemList
        systemListRepository.insertSystemList(new SystemList(house.getHouseId(), messageSource.getMessage("systemListName", null, locale)));
        //TODO: remove this before deployment
        storageRepository.save(new Storage(house.getHouseId(), messageSource.getMessage("fridge", null, locale), new Numrange((float) 1, (float) 5)));
        return house;
    }

    @Transactional
    @Override
    public House updateHouse(
            String username, long houseId, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber, Locale locale
    ) throws EntityNotFoundException, EntityException, InsufficientPrivilegesException {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale)));
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        Characteristics characteristics = new Characteristics(
                babiesNumber,
                childrenNumber,
                adultsNumber,
                seniorsNumber
        );
        house.setHouseName(name);
        house.setHouseCharacteristics(characteristics);
        return house;
    }

    @Transactional
    @Override
    public void deleteHouseByHouseId(String username, long houseId, Locale locale) throws EntityNotFoundException, EntityException, InsufficientPrivilegesException {
        if (!existsHouseByHouseId(houseId))
            throw new EntityNotFoundException(String.format("House Id %d does not exist.", houseId), messageSource.getMessage("house_Id_Not_Exist", new Object[]{houseId}, locale));
        authorizationProvider.checkUserAuthorizationToAdminHouse(username, houseId);
        // Remover a casa bem como todas as relações das quais a casa seja parte integrante
        houseRepository.deleteCascadeHouseById(houseId);
    }
}
