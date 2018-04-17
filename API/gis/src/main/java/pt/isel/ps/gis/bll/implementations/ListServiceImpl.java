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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    public boolean existsListByListId(ListId listId) {
        return listRepository.existsById(listId);
    }

    @Override
    public Optional<List> getListByListId(ListId listId) {
        return listRepository.findById(listId);
    }

    @Override
    public Iterable<List> getListsByHouseId(Long houseId) {
        throw new NotImplementedException();
        //TODO
    }

    @Override
    public Iterable<List> getListsByHouseIdFiltered(Long houseId, ListFilters filters) {
        throw new NotImplementedException();
        //TODO
    }

    @Override
    public UserList addUserList(UserList list) throws EntityException {
        userListRepository.insertUserList(list);
        return null;    //TODO
    }


    @Override
    public List updateList(List list) throws EntityNotFoundException {
        if (!existsListByListId(list.getId()))
            throw new EntityNotFoundException(String.format("List with ID {%d, %d} does not exist.",
                    list.getId().getHouseId(), list.getId().getListId()));
        return listRepository.save(list);
    }

    @Override
    public void deleteList(ListId listId) throws EntityNotFoundException {
        if (!existsListByListId(listId))
            throw new EntityNotFoundException(String.format("List with ID {%d, %d} does not exist.",
                    listId.getHouseId(), listId.getListId()));
        listRepository.deleteById(listId);
    }
}
