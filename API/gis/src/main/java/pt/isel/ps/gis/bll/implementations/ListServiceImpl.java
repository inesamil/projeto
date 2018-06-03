package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.dal.repositories.ListRepository;
import pt.isel.ps.gis.dal.repositories.SystemListRepository;
import pt.isel.ps.gis.dal.repositories.UserListRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.*;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.Optional;

@Service
public class ListServiceImpl implements ListService {

    private final ListRepository listRepository;
    private final UserListRepository userListRepository;
    private final SystemListRepository systemListRepository;

    public ListServiceImpl(ListRepository listRepository, UserListRepository userListRepository, SystemListRepository systemListRepository) {
        this.listRepository = listRepository;
        this.userListRepository = userListRepository;
        this.systemListRepository = systemListRepository;
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
    public List getListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException {
        List list = listRepository
                .findById(new ListId(houseId, listId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.",
                listId, houseId)));
        list.getListproducts().size();
        return list;
    }

    @Override
    public java.util.List<List> getAvailableListsByUserUsername(String username, AvailableListFilters filters) throws EntityException {
        ValidationsUtils.validateUserUsername(username);
        return listRepository.findAvailableListsByUserUsername(username, filters.houses, filters.systemLists, filters.listsFromUser, filters.sharedLists);
    }

    @Override
    public UserList addUserList(UserList list) throws EntityException {
        if (userListRepository.existsById(list.getId()))
            throw new EntityException(String.format("List with ID %d in the house with ID %d already exists.",
                    list.getId().getListId(), list.getId().getHouseId()));
        return userListRepository.insertUserList(list);
    }

    @Transactional
    @Override
    public List updateList(long houseId, short listId, String listName, boolean listShareable) throws EntityException, EntityNotFoundException {
        ListId id = new ListId(houseId, listId);
        // Verify list existence
        List list = listRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.",
                id.getListId(), id.getHouseId())));
        // Verify list type - system lists cannot be updated
        if (isSystemListType(list))
            throw new UnsupportedOperationException(String.format("The list with ID %d in the house with ID %d cannot be updated.",
                    id.getListId(), id.getHouseId()));
        String username = list.getUserlist().getUsersUsername();
        UserList userList = new UserList(houseId, listId, listName, username, listShareable);
        // Update list
        userListRepository.save(userList);//TODO: necessario guadar nos 2 repositórios?
        list = listRepository.save(userList.getList());
        list.getListproducts().size();
        return list;
    }

    @Override
    public void deleteSystemListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException {
        ListId id = new ListId(houseId, listId);
        checkListId(id);
        systemListRepository.deleteById(new SystemListId(houseId, listId));
        listRepository.deleteById(id);
    }

    @Override
    public void deleteUserListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException {
        ListId id = new ListId(houseId, listId);
        checkListId(id);
        userListRepository.deleteCascadeUserListById(new UserListId(houseId, listId));
    }

    private void checkListId(ListId id) throws EntityNotFoundException {
        if (!listRepository.existsById(id))
            throw new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.",
                    id.getListId(), id.getHouseId()));
    }
}
