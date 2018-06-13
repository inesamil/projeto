package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.dal.repositories.AllergyRepository;
import pt.isel.ps.gis.dal.repositories.HouseAllergyRepository;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.HouseAllergyId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseAllergyServiceImpl implements HouseAllergyService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";
    private static final String ALLERGEN_NOT_EXIST = "Allergen does not exist.";
    private static final String ALLERGY_NOT_EXIST = "Allergy does not exist.";

    private final HouseAllergyRepository houseAllergyRepository;
    private final UserHouseRepository membersRepository;
    private final HouseRepository houseRepository;
    private final AllergyRepository allergyRepository;

    public HouseAllergyServiceImpl(HouseAllergyRepository houseAllergyRepository, UserHouseRepository membersRepository, HouseRepository houseService, AllergyRepository allergyRepository) {
        this.houseAllergyRepository = houseAllergyRepository;
        this.membersRepository = membersRepository;
        this.houseRepository = houseService;
        this.allergyRepository = allergyRepository;
    }

    @Override
    public boolean existsHouseAllergyByHouseAllergyId(long houseId, String allergy) throws EntityException {
        return houseAllergyRepository.existsById(new HouseAllergyId(houseId, allergy));
    }

    @Override
    public List<HouseAllergy> getAllergiesByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return houseAllergyRepository.findAllById_HouseId(houseId);
    }

    @Override
    public List<HouseAllergy> associateHouseAllergies(long houseId, HouseAllergy[] allergies) throws EntityNotFoundException, EntityException {
        List<HouseAllergy> houseAllergies = new ArrayList<>();
        for (HouseAllergy houseAllergy : allergies) {
            houseAllergies.add(associateHouseAllergy(houseId, houseAllergy.getId().getAllergyAllergen(), houseAllergy.getHouseallergyAllergicsnum()));
        }
        return houseAllergies;
    }

    @Override
    public HouseAllergy associateHouseAllergy(long houseId, String allergen, short allergicsNum) throws EntityNotFoundException, EntityException {
        HouseAllergy houseAllergy = new HouseAllergy(houseId, allergen, allergicsNum);
        checkHouse(houseId);
        checkAllergy(allergen);
        if (existsHouseAllergyByHouseAllergyId(houseId, allergen))
            houseAllergy = updateHouseAllergy(houseAllergy);
        else
            houseAllergy = addHouseAllergy(houseAllergy);
        return houseAllergy;
    }

    @Override
    public void deleteHouseAllergyByHouseAllergyId(long houseId, String allergen) throws EntityException, EntityNotFoundException {
        HouseAllergyId id = new HouseAllergyId(houseId, allergen);
        checkAllergen(houseId, allergen);
        houseAllergyRepository.deleteById(id);
    }

    @Override
    public void deleteAllHouseAllergiesByHouseId(long houseId) throws EntityNotFoundException {
        checkHouse(houseId);
        houseAllergyRepository.deleteAllById_HouseId(houseId);
    }

    private HouseAllergy addHouseAllergy(HouseAllergy houseAllergy) throws EntityException {
        // Total Membros na casa
        int totalMembers = membersRepository.findAllById_HouseId(houseAllergy.getId().getHouseId()).size();
        if (houseAllergy.getHouseallergyAllergicsnum() > totalMembers)
            throw new EntityException(String.format("Cannot add allergy in the house wih ID %d, there are more allergics than members in the house.",
                    houseAllergy.getId().getHouseId()));
        return houseAllergyRepository.save(houseAllergy);
    }

    private HouseAllergy updateHouseAllergy(HouseAllergy houseAllergy) {
        return houseAllergyRepository.save(houseAllergy);
    }

    private void checkHouse(long houseId) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(HOUSE_NOT_EXIST);
    }

    private void checkAllergy(String allergy) throws EntityNotFoundException {
        if (!allergyRepository.existsById(allergy))
            throw new EntityNotFoundException(ALLERGY_NOT_EXIST);
    }

    private void checkAllergen(long houseId, String allergen) throws EntityException, EntityNotFoundException {
        if (!existsHouseAllergyByHouseAllergyId(houseId, allergen))
            throw new EntityNotFoundException(ALLERGEN_NOT_EXIST);
    }
}
