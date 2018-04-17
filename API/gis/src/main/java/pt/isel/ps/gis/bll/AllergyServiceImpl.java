package pt.isel.ps.gis.bll;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.dal.repositories.AllergyRepository;
import pt.isel.ps.gis.model.Allergy;

import java.util.List;

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
