package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.dal.repositories.*;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.*;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

@Service
public class ListServiceImpl implements ListService {

    private final HouseRepository houseRepository;
    private final ListRepository listRepository;
    private final UserListRepository userListRepository;
    private final SystemListRepository systemListRepository;
    private final UsersRepository usersRepository;
    private final ListProductRepository listProductRepository;

    public ListServiceImpl(HouseRepository houseRepository, ListRepository listRepository, UserListRepository userListRepository, SystemListRepository systemListRepository, UsersRepository usersRepository, ListProductRepository listProductRepository) {
        this.houseRepository = houseRepository;
        this.listRepository = listRepository;
        this.userListRepository = userListRepository;
        this.systemListRepository = systemListRepository;
        this.usersRepository = usersRepository;
        this.listProductRepository = listProductRepository;
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

    @Override
    public java.util.List<List> getListsByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        checkHouseId(houseId);
        return listRepository.findAllById_HouseId(houseId);
    }

    @Transactional
    @Override
    public List getListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException {
        List list = listRepository
                .findById(new ListId(houseId, listId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.",
                        listId, houseId)));
        list.getListproducts().size();
        list.getHouseByHouseId().getHouseName();
        return list;
    }

    @Override
    public java.util.List<List> getAvailableListsByUserUsername(String username, AvailableListFilters filters) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        checkUserUsername(username);
        return listRepository.findAvailableListsByUserUsername(username, filters.houses, filters.systemLists, filters.listsFromUser, filters.sharedLists);
    }

    @Transactional
    @Override
    public List addUserList(Long houseId, String listName, String username, Boolean listShareable) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateListName(listName);
        ValidationsUtils.validateListShareable(listShareable);
        checkHouseId(houseId);
        checkUserUsername(username);
        UserList userList = userListRepository.insertUserList(houseId, listName, username, listShareable);
        List list = userList.getList();
        list.setListproducts(listProductRepository.findAllById_HouseIdAndId_ListId(list.getId().getHouseId(), list.getId().getListId()));
        return list;
    }

    @Transactional
    @Override
    public List updateList(long houseId, short listId, String listName, Boolean listShareable) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        ListId id = new ListId(houseId, listId);
        // Verify list existence
        List list = listRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.",
                        id.getListId(), id.getHouseId())));
        // Verify list type - system lists cannot be updated
        if (isSystemListType(list))
            throw new InsufficientPrivilegesException("You don't have permissions to update this list.");
        UserList userList = new UserList(houseId, listId, listName, list.getUserlist().getUsersId(), listShareable);
        // Update list
        userListRepository.save(userList);
        list = listRepository.save(userList.getList());
        list.getListproducts().size();
        return list;
    }

    @Override
    public void deleteSystemListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        ListId id = new ListId(houseId, listId);
        checkListId(id);
        systemListRepository.deleteById(new SystemListId(houseId, listId));
        listRepository.deleteById(id);
    }

    @Override
    public void deleteUserListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        ListId id = new ListId(houseId, listId);
        checkListId(id);
        userListRepository.deleteCascadeUserListById(new UserListId(houseId, listId));
    }

    private void checkHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(String.format("The house with ID %d does not exist.", houseId));
    }

    private void checkUserUsername(String username) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsByUsersUsername(username))
            throw new EntityNotFoundException(String.format("The user with username %s does not exist.", username));
    }

    private void checkListId(ListId id) throws EntityNotFoundException {
        if (!listRepository.existsById(id))
            throw new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.",
                    id.getListId(), id.getHouseId()));
    }
}
