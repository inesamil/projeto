package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.SystemList;

public interface SystemListRepositoryCustom {

    /**
     * Insert system list
     *
     * @param systemList instance of system list to be inserted.
     * @return System list inserted with id setted up
     */
    SystemList insertSystemList(SystemList systemList);
}
