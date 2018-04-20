package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListId;
import pt.isel.ps.gis.model.UserList;

import java.util.Optional;

public interface ListService {

    /**
     * Verifica se uma dada lista existe através do seu ID
     *
     * @param listId identificador da lista
     * @return true se a lista existir, false caso contrário
     */
    boolean existsListByListId(ListId listId);

    /**
     * Obter uma lista através do seu ID
     *
     * @param listId identificador da lista
     * @return List
     */
    Optional<List> getListByListId(ListId listId);

    /**
     * Listar as listas de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<List>
     */
    java.util.List<List> getListsByHouseId(long houseId, String username);

    /**
     * Listar as listas filtradas de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<List>
     */
    java.util.List<List> getListsByHouseIdFiltered(long houseId, ListFilters filters);

    /**
     * Adicionar uma lista a uma casa
     *
     * @param list lista a adicionar
     * @return List
     */
    UserList addUserList(UserList list);

    /**
     * Atualizar uma lista duma casa
     *
     * @param list lista atualizada
     * @return List
     */
    List updateList(List list) throws EntityNotFoundException;

    /**
     * Remover uma lista duma casa
     *
     * @param listId identificador da lista
     */
    void deleteList(long houseId, short listId) throws EntityException, EntityNotFoundException;

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
}
