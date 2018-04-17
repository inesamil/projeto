package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.model.Allergy;

import java.util.List;

public interface AllergyService {

    Iterable<Allergy> getAllergies();
}
