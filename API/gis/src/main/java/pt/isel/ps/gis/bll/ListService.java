package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.UserList;

import java.util.Optional;

public interface ListService {

    /**
     * Verifica se uma dada lista existe através do seu ID
     *
     * @param houseId identificador da lista
     * @param listId  identificador da lista
     * @return true se a lista existir, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsListByListId(long houseId, short listId) throws EntityException;

    /**
     * Verifica se uma dada lista é uma lista de sistema
     *
     * @param list lista a verificar
     * @return true se a lista for de sistema, false caso contrário
     */
    boolean isSystemListType(List list);

    /**
     * Verifica se uma dada lista é uma lista de utilizador
     *
     * @param list lista a verificar
     * @return true se a lista for de utilizador, false caso contrário
     */
    boolean isUserListType(List list);

    /**
     * Obter uma lista através do seu ID
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @return Optional<List>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Optional<List> getListByListId(long houseId, short listId) throws EntityException;

    /**
     * Listar as listas através do username do user
     *
     * @param username identificador do user
     * @return List<List>
     * @throws EntityException se od parâmetros recebeidos forem inválidos
     */
    java.util.List<List> getAvailableListsByUserUsername(String username, AvailableListFilters filters) throws EntityException;

    /**
     * Adicionar uma lista a uma casa
     *
     * @param list lista a adicionar
     * @return UserList
     */
    UserList addUserList(UserList list) throws EntityException;

    /**
     * Atualizar uma lista duma casa
     *
     * @param list lista atualizada
     * @return List
     * @throws EntityNotFoundException se a lista especificada não existir na casa particularizada
     */
    List updateList(List list) throws EntityNotFoundException;

    /**
     * Remover uma dada lista do sistema da casa
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se a lista especificada não existir na casa particularizada
     */
    void deleteSystemListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException;

    /**
     * Remover uma dada lista de um utilizador
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se a lista especificada não existir na casa particularizada
     */
    void deleteUserListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException;

    /**
     * Filtros - filtragem das listas
     */
    class AvailableListFilters {
        public final Long[] houses;
        public final Boolean systemLists;
        public final Boolean listsFromUser;
        public final Boolean sharedLists;

        public AvailableListFilters(Long[] houses, Boolean systemLists, Boolean listsFromUser, Boolean sharedLists) {
            this.houses = houses;
            this.systemLists = systemLists;
            this.listsFromUser = listsFromUser;
            this.sharedLists = sharedLists;
        }
    }
}
