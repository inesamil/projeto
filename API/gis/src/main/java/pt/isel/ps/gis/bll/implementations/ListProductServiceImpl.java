package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.ListProductService;
import pt.isel.ps.gis.dal.repositories.ListProductRepository;
import pt.isel.ps.gis.dal.repositories.ListRepository;
import pt.isel.ps.gis.dal.repositories.ProductRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.ListId;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.ListProductId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class ListProductServiceImpl implements ListProductService {

    private static final String LIST_NOT_EXIST = "List does not exist in this house.";
    private static final String PRODUCT_NOT_EXIST = "Product does not exist.";

    private final ListRepository listRepository;
    private final ProductRepository productRepository;
    private final ListProductRepository listProductRepository;

    public ListProductServiceImpl(ListRepository listRepository, ProductRepository productRepository, ListProductRepository listProductRepository) {
        this.listRepository = listRepository;
        this.productRepository = productRepository;
        this.listProductRepository = listProductRepository;
    }

    @Override
    public boolean existsListProductByListProductId(long houseId, short listId, int productId) throws EntityException {
        return listProductRepository.existsById(new ListProductId(houseId, listId, productId));
    }

    @Override
    public ListProduct getListProductByListProductId(long houseId, short listId, int productId) throws EntityException {
        return listProductRepository
                .findById(new ListProductId(houseId, listId, productId))
                .orElseThrow(() -> new EntityException(String.format("Product with ID %d does not exist in the list with ID %d in the house with ID %d.",
                        productId, listId, houseId)));
    }

    @Override
    public List<ListProduct> getListProductsByListId(long houseId, short listId) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        checkListId(new ListId(houseId, listId));
        return listProductRepository.findAllById_HouseIdAndId_ListId(houseId, listId);
    }

    @Override
    public ListProduct associateListProduct(long houseId, short listId, Integer productId, String brand, Short quantity) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        checkListId(new ListId(houseId, listId));
        checkProductId(productId);
        ListProduct listProduct = new ListProduct(houseId, listId, productId, brand, quantity);
        return listProductRepository.save(listProduct);
    }

    @Override
    public void deleteListProductByListProductId(long houseId, short listId, int productId) throws EntityException, EntityNotFoundException {
        // TODO transacional?
        ListProductId id = new ListProductId(houseId, listId, productId);
        checkListProductId(id);
        listProductRepository.deleteById(id);
    }

    private void checkListProductId(ListProductId listProductId) throws EntityNotFoundException {
        if (!listProductRepository.existsById(listProductId))
            throw new EntityNotFoundException(String.format("Product with ID %d does not exist in the list with ID %d in the house with ID %d.",
                    listProductId.getProductId(), listProductId.getListId(), listProductId.getHouseId()));
    }

    private void checkProductId(int productId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateProductId(productId);
        if (!productRepository.existsById(productId))
            throw new EntityNotFoundException(PRODUCT_NOT_EXIST);
    }

    private void checkListId(ListId listId) throws EntityNotFoundException {
        if (!listRepository.existsById(listId))
            throw new EntityNotFoundException(LIST_NOT_EXIST);
    }
}
