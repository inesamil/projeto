package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.dal.repositories.*;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.*;
import pt.isel.ps.gis.utils.AuthorizationProvider;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.Locale;
import java.util.Optional;

@Service
public class ListServiceImpl implements ListService {

    private final HouseRepository houseRepository;
    private final ListRepository listRepository;
    private final UserListRepository userListRepository;
    private final SystemListRepository systemListRepository;
    private final UsersRepository usersRepository;
    private final ListProductRepository listProductRepository;

    private final MessageSource messageSource;

    private final AuthorizationProvider authorizationProvider;

    public ListServiceImpl(HouseRepository houseRepository, ListRepository listRepository, UserListRepository userListRepository, SystemListRepository systemListRepository, UsersRepository usersRepository, ListProductRepository listProductRepository, MessageSource messageSource, AuthorizationProvider authorizationProvider) {
        this.houseRepository = houseRepository;
        this.listRepository = listRepository;
        this.userListRepository = userListRepository;
        this.systemListRepository = systemListRepository;
        this.usersRepository = usersRepository;
        this.listProductRepository = listProductRepository;
        this.messageSource = messageSource;
        this.authorizationProvider = authorizationProvider;
    }

    @Override
    public boolean existsListByListId(long houseId, short listId) throws EntityException {
        return listRepository.existsById(new ListId(houseId, listId));
    }

    @Override
    public boolean isSystemListType(List list) {
        return list.getListType().equals(RestrictionsUtils.LIST_TYPE.system.name());
    }

    @Override
    public boolean isUserListType(List list) {
        return list.getListType().equals(RestrictionsUtils.LIST_TYPE.user.name());
    }

    @Transactional
    @Override
    public java.util.List<List> getListsByHouseId(String username, long houseId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouseId(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        return listRepository.findAllById_HouseId(houseId);
    }

    @Transactional
    @Override
    public List getListByListId(String username, long houseId, short listId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouseId(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        List list = listRepository
                .findById(new ListId(houseId, listId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.", listId, houseId), messageSource.getMessage("list_Id_Not_Exist", new Object[]{listId, houseId}, locale)));
        list.getListproducts().size();
        list.getHouseByHouseId().getHouseName();
        return list;
    }

    @Transactional
    @Override
    public java.util.List<List> getAvailableListsByUserUsername(String authenticatedUsername, String username, AvailableListFilters filters, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        authorizationProvider.checkUserAuthorizationToAccessResource(authenticatedUsername, username);
        checkUserUsername(username, locale);
        return listRepository.findAvailableListsByUserUsername(username, filters.houses, filters.systemLists, filters.listsFromUser, filters.sharedLists);
    }

    @Transactional
    @Override
    public List addUserList(String authenticatedUsername, Long houseId, String listName, String username, Boolean listShareable, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        authorizationProvider.checkUserAuthorizationToAccessResource(authenticatedUsername, username);
        ValidationsUtils.validateListName(listName);
        ValidationsUtils.validateListShareable(listShareable);
        checkHouseId(houseId, locale);
        checkUserUsername(username, locale);
        UserList userList = userListRepository.insertUserList(houseId, listName, username, listShareable);
        List list = userList.getList();
        list.setListproducts(listProductRepository.findAllById_HouseIdAndId_ListId(list.getId().getHouseId(), list.getId().getListId()));
        return list;
    }

    @Transactional
    @Override
    public List updateList(long houseId, short listId, String listName, Boolean listShareable, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        ListId id = new ListId(houseId, listId);
        // Verify list existence
        List list = listRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.", listId, houseId), messageSource.getMessage("list_Id_Not_Exist", new Object[]{id.getListId(), id.getHouseId()}, locale)));
        // Verify list type - system lists cannot be updated
        if (isSystemListType(list))
            throw new InsufficientPrivilegesException("You don't have permissions to update this list.", messageSource.getMessage("not_Permission_Update_List", null, locale));
        UserList userList = new UserList(houseId, listId, listName, list.getUserlist().getUsersId(), listShareable);
        // Update list
        userListRepository.save(userList);
        list = listRepository.save(userList.getList());
        list.getListproducts().size();
        return list;
    }

    @Transactional
    @Override
    public void deleteSystemListByListId(long houseId, short listId, Locale locale) throws EntityException, EntityNotFoundException {
        ListId id = new ListId(houseId, listId);
        checkListId(id, locale);
        systemListRepository.deleteById(new SystemListId(houseId, listId));
        listRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteUserListByListId(String username, long houseId, short listId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        ListId id = new ListId(houseId, listId);
        checkListId(id, locale);
        UserListId userListId = new UserListId(houseId, listId);
        Optional<UserList> userList = userListRepository.findById(userListId);
        if (userList.isPresent() && !userList.get().getUsersByUsersId().getUsersUsername().equals(username)) {
            throw new InsufficientPrivilegesException("You don't have permissions to delete this list.", messageSource.getMessage("not_Permission_Delete_List", null, locale));
        }
        userListRepository.deleteCascadeUserListById(userListId);
    }

    private void checkHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(String.format("House Id %d does not exist.", houseId), messageSource.getMessage("house_Id_Not_Exist", new Object[]{houseId}, locale));
    }

    private void checkUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsByUsersUsername(username))
            throw new EntityNotFoundException(String.format("Username %s does not exist.", username), messageSource.getMessage("user_Username_Not_Exist", new Object[]{username}, locale));
    }

    private void checkListId(ListId id, Locale locale) throws EntityNotFoundException {
        if (!listRepository.existsById(id))
            throw new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.", id.getListId(), id.getHouseId()), messageSource.getMessage("list_Id_Not_Exist", new Object[]{id.getListId(), id.getHouseId()}, locale));
    }
}
