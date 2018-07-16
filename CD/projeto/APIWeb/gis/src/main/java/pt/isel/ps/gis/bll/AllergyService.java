package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.model.Allergy;

import java.util.List;

public interface AllergyService {

    /**
     * Listar todas as alergias
     *
     * @return List<Allergy>
     */
    List<Allergy> getAllergies();
}
