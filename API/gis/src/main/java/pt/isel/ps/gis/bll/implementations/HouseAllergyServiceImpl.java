package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.dal.repositories.AllergyRepository;
import pt.isel.ps.gis.dal.repositories.HouseAllergyRepository;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.*;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class HouseAllergyServiceImpl implements HouseAllergyService {

    private final HouseAllergyRepository houseAllergyRepository;
    private final UserHouseRepository membersRepository;
    private final HouseRepository houseRepository;
    private final AllergyRepository allergyRepository;

    private final MessageSource messageSource;

    public HouseAllergyServiceImpl(HouseAllergyRepository houseAllergyRepository, UserHouseRepository membersRepository, HouseRepository houseService, AllergyRepository allergyRepository, MessageSource messageSource) {
        this.houseAllergyRepository = houseAllergyRepository;
        this.membersRepository = membersRepository;
        this.houseRepository = houseService;
        this.allergyRepository = allergyRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean existsHouseAllergyByHouseAllergyId(long houseId, String allergy) throws EntityException {
        return houseAllergyRepository.existsById(new HouseAllergyId(houseId, allergy));
    }

    @Transactional
    @Override
    public List<HouseAllergy> getAllergiesByHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        checkHouse(houseId, locale);
        List<HouseAllergy> houseAllergies = houseAllergyRepository.findAllById_HouseId(houseId);
        List<HouseAllergy> allergies = new ArrayList<>();
        for (Allergy allergy : allergyRepository.findAll()) {
            HouseAllergy houseAllergy = houseAllergies.stream()
                    .filter(ha -> ha.getId().getAllergyAllergen().equals(allergy.getAllergyAllergen())).findFirst().orElse(null);
            if (houseAllergy == null)
                houseAllergy = new HouseAllergy(houseId, allergy.getAllergyAllergen(), (short) 0);
            allergies.add(houseAllergy);
        }
        return allergies;
    }

    @Transactional
    @Override
    public List<HouseAllergy> associateHouseAllergies(long houseId, HouseAllergy[] allergies, Locale locale) throws EntityNotFoundException, EntityException {
        List<HouseAllergy> houseAllergies = new ArrayList<>();
        for (HouseAllergy houseAllergy : allergies) {
            houseAllergies.add(associateHouseAllergy(houseId, houseAllergy.getId().getAllergyAllergen(), houseAllergy.getHouseallergyAllergicsnum(), locale));
        }
        return houseAllergies;
    }

    @Transactional
    @Override
    public HouseAllergy associateHouseAllergy(long houseId, String allergen, Short allergicsNum, Locale locale) throws EntityNotFoundException, EntityException {
        ValidationsUtils.validateHouseAllergyAllergicsNum(allergicsNum);
        // Total Membros na casa
        short totalMembers = getTotalHouseMembers(houseId, locale);
        if (allergicsNum > totalMembers)
            throw new EntityException(String.format("Cannot add allergy in the house wih ID %d, there are more allergics than members in the house.", houseId), messageSource.getMessage("more_Allergics_Than_Members", new Object[]{houseId}, locale));
        Optional<HouseAllergy> optionalHouseAllergy = houseAllergyRepository.findById(new HouseAllergyId(houseId, allergen));
        HouseAllergy houseAllergy;
        if (optionalHouseAllergy.isPresent()) {
            houseAllergy = optionalHouseAllergy.get();
            houseAllergy.setHouseallergyAllergicsnum(allergicsNum);
        } else {
            checkHouse(houseId, locale);
            checkAllergy(allergen, locale);
            houseAllergy = new HouseAllergy(houseId, allergen, allergicsNum);
            houseAllergyRepository.save(houseAllergy);
        }
        return houseAllergy;
    }

    @Override
    public void deleteHouseAllergyByHouseAllergyId(long houseId, String allergen, Locale locale) throws EntityException, EntityNotFoundException {
        HouseAllergyId id = new HouseAllergyId(houseId, allergen);
        checkAllergen(houseId, allergen, locale);
        houseAllergyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllHouseAllergiesByHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        checkHouse(houseId, locale);
        houseAllergyRepository.deleteAllById_HouseId(houseId);
    }

    private void checkHouse(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale));
    }

    private void checkAllergy(String allergy, Locale locale) throws EntityNotFoundException {
        if (!allergyRepository.existsById(allergy))
            throw new EntityNotFoundException("Allergy does not exist.", messageSource.getMessage("allergy_Not_Exist", null, locale));
    }

    private void checkAllergen(long houseId, String allergen, Locale locale) throws EntityException, EntityNotFoundException {
        if (!existsHouseAllergyByHouseAllergyId(houseId, allergen))
            throw new EntityNotFoundException("Allergen does not exist.", messageSource.getMessage("allergen_Not_Exist", null, locale));
    }

    private short getTotalHouseMembers(long houseId, Locale locale) throws EntityNotFoundException {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale)));
        Characteristics characteristics = house.getHouseCharacteristics();
        return (short) (characteristics.getHouse_babiesNumber() + characteristics.getHouse_childrenNumber()
                + characteristics.getHouse_adultsNumber() + characteristics.getHouse_seniorsNumber());
    }
}
