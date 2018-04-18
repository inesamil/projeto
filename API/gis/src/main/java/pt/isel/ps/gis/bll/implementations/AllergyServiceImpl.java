package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.AllergyService;
import pt.isel.ps.gis.dal.repositories.AllergyRepository;
import pt.isel.ps.gis.model.Allergy;

@Service
public class AllergyServiceImpl implements AllergyService {

    private final AllergyRepository allergyRepository;

    public AllergyServiceImpl(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    @Override
    public Iterable<Allergy> getAllergies() {
        return allergyRepository.findAll();
    }
}
