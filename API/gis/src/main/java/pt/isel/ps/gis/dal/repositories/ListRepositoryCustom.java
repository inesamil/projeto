package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.List;

public interface ListRepositoryCustom {

    java.util.List<List> findListsFiltered(Long houseId, Boolean system, String username, Boolean shared);
}
