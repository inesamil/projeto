package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.ListProductService;
import pt.isel.ps.gis.dal.repositories.ListProductRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.ListProductId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Optional;

public class ListProductServiceImpl implements ListProductService {

    private final ListProductRepository listProductRepository;

    public ListProductServiceImpl(ListProductRepository listProductRepository) {
        this.listProductRepository = listProductRepository;
    }

    @Override
    public boolean existsListProductByListProductId(long houseId, short listId, int categoryId, int productId) throws EntityException {
        return listProductRepository.existsById(new ListProductId(houseId, listId, categoryId, productId));
    }

    @Override
    public Optional<ListProduct> getListProductByListProductId(long houseId, short listId, int categoryId, int productId) throws EntityException {
        return listProductRepository.findById(new ListProductId(houseId, listId, categoryId, productId));
    }

    @Override
    public List<ListProduct> getListProductsByListId(long houseId, short listId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateListId(listId);
        return listProductRepository.findAllById_HouseIdAndId_ListId(houseId, listId);
    }

    @Override
    public ListProduct addListProduct(ListProduct listProduct) {
        return listProductRepository.save(listProduct);
    }

    @Override
    public ListProduct updateListProduct(ListProduct listProduct) throws EntityNotFoundException {
        ListProductId id = listProduct.getId();
        if (!listProductRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Product with ID %d from the category with ID %d does not exist in the list with ID %d in the house with ID %d.",
                    id.getProductId(), id.getCategoryId(), id.getListId(), id.getHouseId()));
        return listProductRepository.save(listProduct);
    }

    @Override
    public void deleteListProductByListProductId(long houseId, short listId, int categoryId, int productId) throws EntityException, EntityNotFoundException {
        ListProductId id = new ListProductId(houseId, listId, categoryId, productId);
        if (!listProductRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Product with ID %d from the category with ID %d does not exist in the list with ID %d in the house with ID %d.",
                    productId, categoryId, listId, houseId));
        listProductRepository.deleteById(id);
    }
}
