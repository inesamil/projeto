package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.dal.repositories.ListRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListId;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

public class ListServiceImpl implements ListService {

    private final ListRepository listRepository;

    public ListServiceImpl(ListRepository listRepository) {
        this.listRepository = listRepository;
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
    public List addList(List list) throws EntityException {
        List newList = list;
        if (list.getId() != null){
            // É preciso garantir que listId está a NULL, para ser feita inserção da nova lista.
            // Caso contrário, poderia ser atualizada uma lista já existente.
            newList = new List(list.getListName(), list.getListType());
        }
        return listRepository.save(newList);
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
