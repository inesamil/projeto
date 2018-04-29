package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.dal.repositories.HouseAllergyRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.HouseAllergyId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

public class HouseAllergyServiceImpl implements HouseAllergyService {

    private final HouseAllergyRepository houseAllergyRepository;
    private final UserHouseRepository membersRepository;

    public HouseAllergyServiceImpl(HouseAllergyRepository houseAllergyRepository, UserHouseRepository membersRepository) {
        this.houseAllergyRepository = houseAllergyRepository;
        this.membersRepository = membersRepository;
    }

    @Override
    public boolean existsHouseAllergyByHouseAllergyId(long houseId, String allergy) throws EntityException {
        return houseAllergyRepository.existsById(new HouseAllergyId(houseId, allergy));
    }

    @Override
    public List<HouseAllergy> getAllergiesByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return houseAllergyRepository.findAllById_HouseId(houseId);
    }

    @Override
    public HouseAllergy addHouseAllergy(HouseAllergy houseAllergy) throws EntityException {
        if (houseAllergy.getId() != null && houseAllergyRepository.existsById(houseAllergy.getId()))
            throw new EntityException(String.format("Allergy with name %s in the house with ID %d already exists.",
                    houseAllergy.getId().getAllergyAllergen(), houseAllergy.getId().getHouseId()));
        // Total Membros na casa
        int totalMembers = membersRepository.findAllById_HouseId(houseAllergy.getId().getHouseId()).size();
        if (houseAllergy.getHouseallergyAlergicsnum() > totalMembers)
            throw new EntityException(String.format("Cannot add allergy in the house wih ID %d, there are more allergics than members in the house.",
                    houseAllergy.getId().getHouseId()));
        return houseAllergyRepository.save(houseAllergy);
    }

    @Override
    public HouseAllergy updateHouseAllergy(HouseAllergy houseAllergy) throws EntityNotFoundException {
        HouseAllergyId id = houseAllergy.getId();
        if (houseAllergyRepository.existsById(id))
            throw new EntityNotFoundException(String.format("The allergy with name %s does not exist in the house with ID %d.",
                    id.getAllergyAllergen(), id.getHouseId()));
        return houseAllergyRepository.save(houseAllergy);
    }

    @Override
    public void deleteHouseAllergyByHouseAllergyId(long houseId, String allergy) throws EntityException, EntityNotFoundException {
        HouseAllergyId id = new HouseAllergyId(houseId, allergy);
        if (houseAllergyRepository.existsById(id))
            throw new EntityNotFoundException(String.format("The allergy with name %s does not exist in the house with ID %d.",
                    allergy, houseId));
        houseAllergyRepository.deleteById(id);
    }

    @Override
    public HouseAllergy increaseHouseAllergyAllergicsNumber(long houseId, String allergen, short numberOfAllergics) {
        return null;
    }


}
