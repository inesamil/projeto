package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.dal.bdModel.Allergy;
import pt.isel.ps.gis.dal.bdModel.HouseAllergy;

import java.util.List;

public interface AllergyService {

    List<Allergy> getAllergies();
}
