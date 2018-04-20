package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.dal.repositories.HouseAllergyRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.HouseAllergyId;

import java.util.List;

public class HouseAllergyServiceImpl implements HouseAllergyService {

    private final HouseAllergyRepository houseAllergyRepository;

    public HouseAllergyServiceImpl(HouseAllergyRepository houseAllergyRepository) {
        this.houseAllergyRepository = houseAllergyRepository;
    }

    @Override
    public boolean existsHouseAllergyByHouseAllergyId(long houseId, String allergy) throws EntityException {
        return houseAllergyRepository.existsById(new HouseAllergyId(houseId, allergy));
    }

    @Override
    public List<HouseAllergy> getAllergiesByHouseId(long houseId) {
        return houseAllergyRepository.findAllById_HouseId(houseId);
    }

    @Override
    public HouseAllergy addHouseAllergy(HouseAllergy houseAllergy) {
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
}
