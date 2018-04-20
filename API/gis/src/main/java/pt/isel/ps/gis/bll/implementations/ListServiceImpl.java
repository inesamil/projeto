package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.ListService;
import pt.isel.ps.gis.dal.repositories.ListRepository;
import pt.isel.ps.gis.dal.repositories.SystemListRepository;
import pt.isel.ps.gis.dal.repositories.UserListRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListId;
import pt.isel.ps.gis.model.UserList;

import java.util.Optional;

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
    public Optional<List> getListByListId(long houseId, short listId) throws EntityException {
        return listRepository.findById(new ListId(houseId, listId));
    }

    @Override
    public java.util.List<List> getListsByHouseId(long houseId, String username) {
       return listRepository.findListsFiltered(houseId, true, username, true);
    }

    @Override
    public java.util.List<List> getListsByHouseIdFiltered(long houseId, ListFilters filters) {
        return listRepository.findListsFiltered(houseId, filters.systemLists, filters.listsFromUser, filters.sharedLists);
    }

    @Override
    public UserList addUserList(UserList list) {
        return userListRepository.insertUserList(list);
    }

    @Override
    public List updateList(List list) throws EntityNotFoundException {
        ListId id = list.getId();
        if (!listRepository.existsById(id))
            throw new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.",
                    id.getListId(), id.getHouseId()));
        return listRepository.save(list);
    }

    @Override
    public void deleteListByListId(long houseId, short listId) throws EntityException, EntityNotFoundException {
        ListId id = new ListId(houseId, listId);
        if (!listRepository.existsById(id))
            throw new EntityNotFoundException(String.format("List with ID %d does not exist in the house with ID %d.",
                    id.getListId(), id.getHouseId()));
        listRepository.deleteById(id);
    }
}
