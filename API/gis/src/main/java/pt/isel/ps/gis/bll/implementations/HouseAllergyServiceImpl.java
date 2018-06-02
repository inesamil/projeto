package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.dal.repositories.HouseAllergyRepository;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.HouseAllergyId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class HouseAllergyServiceImpl implements HouseAllergyService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";
    private static final String ALLERGEN_NOT_EXIST = "Allergen does not exist.";

    private final HouseAllergyRepository houseAllergyRepository;
    private final UserHouseRepository membersRepository;
    private final HouseRepository houseRepository;

    public HouseAllergyServiceImpl(HouseAllergyRepository houseAllergyRepository, UserHouseRepository membersRepository, HouseRepository houseService) {
        this.houseAllergyRepository = houseAllergyRepository;
        this.membersRepository = membersRepository;
        this.houseRepository = houseService;
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
    public HouseAllergy associateHouseAllergy(long houseId, String allergen, short allergicsNum) throws EntityNotFoundException, EntityException {
        checkAllergen(houseId, allergen);
        HouseAllergy houseAllergy = new HouseAllergy(houseId, allergen, allergicsNum);
        if (existsHouseAllergyByHouseAllergyId(houseId, allergen))
            houseAllergy = updateHouseAllergy(houseAllergy);
        else
            houseAllergy = addHouseAllergy(houseAllergy);
        return houseAllergy;
    }

    @Override
    public void deleteHouseAllergyByHouseAllergyId(long houseId, String allergen) throws EntityException, EntityNotFoundException {
        checkAllergen(houseId, allergen);
        houseAllergyRepository.deleteById(new HouseAllergyId(houseId, allergen));
    }

    @Override
    public HouseAllergy increaseHouseAllergyAllergicsNumber(long houseId, String allergen, short numberOfAllergics) {
        // TODO
        return null;
    }

    private HouseAllergy addHouseAllergy(HouseAllergy houseAllergy) throws EntityException {
        // Total Membros na casa
        int totalMembers = membersRepository.findAllById_HouseId(houseAllergy.getId().getHouseId()).size();
        if (houseAllergy.getHouseallergyAlergicsnum() > totalMembers)
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

    private void checkAllergen(long houseId, String allergen) throws EntityException, EntityNotFoundException {
        if (!existsHouseAllergyByHouseAllergyId(houseId, allergen))
            throw new EntityNotFoundException(ALLERGEN_NOT_EXIST);
    }
}
