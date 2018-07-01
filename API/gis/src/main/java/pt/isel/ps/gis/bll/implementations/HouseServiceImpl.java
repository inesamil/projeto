package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.SystemListRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.*;
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

    private final MessageSource messageSource;

    public HouseServiceImpl(HouseRepository houseRepository, UsersRepository usersRepository, UserHouseRepository userHouseRepository, SystemListRepository systemListRepository, MessageSource messageSource) {
        this.houseRepository = houseRepository;
        this.usersRepository = usersRepository;
        this.userHouseRepository = userHouseRepository;
        this.systemListRepository = systemListRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean existsHouseByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return houseRepository.existsById(houseId);
    }

    @Override
    public House getHouseByHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        return houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("house_Not_Exist", null, locale)));
    }

    @Transactional
    @Override
    public List<House> getHousesByUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsByUsersUsername(username))
            throw new EntityNotFoundException(messageSource.getMessage("user_Username_Not_Exist", new String[]{username}, locale));
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
        Users user = usersRepository.findByUsersUsername(username).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("user_Not_Exist", null, locale)));
        house = houseRepository.save(house);
        UserHouse userHouse = userHouseRepository.save(new UserHouse(house.getHouseId(), user.getUsersId(), true));
        userHouse.setUsersByUsersId(user);
        house.setUserhousesByHouseId(new ArrayList<>(Collections.singleton(userHouse)));
        //Create SystemList
        systemListRepository.insertSystemList(new SystemList(house.getHouseId(), messageSource.getMessage("systemListName", null, locale)));
        return house;
    }

    @Transactional
    @Override
    public House updateHouse(
            long houseId, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber, Locale locale
    ) throws EntityNotFoundException, EntityException {
        House house = getHouseByHouseId(houseId, locale);
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
    public void deleteHouseByHouseId(long houseId, Locale locale) throws EntityNotFoundException, EntityException {
        if (!existsHouseByHouseId(houseId))
            throw new EntityNotFoundException(messageSource.getMessage("house_Id_Not_Exist", new Object[]{houseId}, locale));
        // Remover a casa bem como todas as relações das quais a casa seja parte integrante
        houseRepository.deleteCascadeHouseById(houseId);
    }
}
