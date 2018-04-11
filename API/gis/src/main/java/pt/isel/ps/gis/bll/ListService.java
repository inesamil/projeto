package pt.isel.ps.gis.bll;

import java.util.List;

public interface ListService {

    /**
     * Filtros - filtragem das listas
     */
    class ListFilters {
        public final boolean systemLists;
        public final String listsFromUser;
        public final boolean sharedLists;

        public ListFilters(boolean systemLists, String listsFromUser, boolean sharedLists) {
            this.systemLists = systemLists;
            this.listsFromUser = listsFromUser;
            this.sharedLists = sharedLists;
        }
    }

    /**
     * Obter uma lista através do seu ID
     * @param houseId identificador da casa
     * @param listId identificador da lista
     * @return List
     */
    pt.isel.ps.gis.dal.bdModel.List getListByListId(Long houseId, Short listId);

    /**
     * Listar as listas de uma casa através do ID da casa
     * @param houseId identificador da casa
     * @return List<List>
     */
    List<pt.isel.ps.gis.dal.bdModel.List> getListsByHouseId(Long houseId);

    /**
     * Listar as listas filtradas de uma casa através do ID da casa
     * @param houseId identificador da casa
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<List>
     */
    List<pt.isel.ps.gis.dal.bdModel.List> getListsByHouseIdFiltered(Long houseId, ListFilters filters);

    /**
     * Adicionar uma lista a uma casa
     * @param list lista a adicionar
     * @return List
     */
    pt.isel.ps.gis.dal.bdModel.List addList(pt.isel.ps.gis.dal.bdModel.List list);

    /**
     * Atualizar uma lista duma casa
     * @param list lista atualizada
     * @return List
     */
    pt.isel.ps.gis.dal.bdModel.List updateList(pt.isel.ps.gis.dal.bdModel.List list);

    /**
     * Remover uma lista duma casa
     * @param houseId identificador da casa
     * @param listId identificador da lista
     */
    void deleteList(Long houseId, Short listId);
}
