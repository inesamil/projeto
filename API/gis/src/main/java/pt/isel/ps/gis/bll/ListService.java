package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.SystemList;

import java.util.Locale;

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
     * Obter as listas de uma casa através do seu ID
     *
     * @param houseId identificador da casa
     * @return List<List>
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se a casa com ID {houseId} não existir
     */
    java.util.List<List> getListsByHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Obter uma lista através do seu ID
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @return List
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se a lista especificada não existir
     */
    List getListByListId(long houseId, short listId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Listar as listas através do username do user
     *
     * @param username identificador do user
     * @return List<List>
     * @throws EntityException         se os parâmetros recebeidos forem inválidos
     * @throws EntityNotFoundException se o utilizador não existir
     */
    java.util.List<List> getAvailableListsByUserUsername(String username, AvailableListFilters filters, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Adicionar uma lista a uma casa
     *
     * @param houseId       identificador da casa
     * @param listName      nome da lista
     * @param username      nome de utilizador do autor da lista
     * @param listShareable indicador de lista partilhável
     * @return UserList
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List addUserList(Long houseId, String listName, String username, Boolean listShareable, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Atualizar uma lista duma casa
     *
     * @param houseId       identificador da casa
     * @param listId        identificador da lista
     * @param listName      nome da lista
     * @param listShareable indicador de lista partilhável
     * @return List
     * @throws EntityNotFoundException se a lista especificada não existir na casa particularizada
     */
    List updateList(long houseId, short listId, String listName, Boolean listShareable, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Remover uma dada lista do sistema da casa
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se a lista especificada não existir na casa particularizada
     */
    void deleteSystemListByListId(long houseId, short listId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Remover uma dada lista de um utilizador
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se a lista especificada não existir na casa particularizada
     */
    void deleteUserListByListId(String username, long houseId, short listId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Filtros - filtragem das listas
     */
    class AvailableListFilters {
        public final Long[] houses;
        public final Boolean systemLists;
        public final Boolean listsFromUser;
        public final Boolean sharedLists;

        public AvailableListFilters(Long[] houses) {
            this.houses = houses;
            this.systemLists = true;
            this.listsFromUser = true;
            this.sharedLists = true;
        }

        public AvailableListFilters(Long[] houses, Boolean systemLists, Boolean listsFromUser, Boolean sharedLists) {
            this.houses = houses;
            this.systemLists = systemLists;
            this.listsFromUser = listsFromUser;
            this.sharedLists = sharedLists;
        }
    }
}
