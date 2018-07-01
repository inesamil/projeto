package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.ListProductService;
import pt.isel.ps.gis.dal.repositories.ListProductRepository;
import pt.isel.ps.gis.dal.repositories.ListRepository;
import pt.isel.ps.gis.dal.repositories.ProductRepository;
import pt.isel.ps.gis.exceptions.EntityAlreadyExistsException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.ListId;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.ListProductId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;

@Service
public class ListProductServiceImpl implements ListProductService {

    private final ListRepository listRepository;
    private final ProductRepository productRepository;
    private final ListProductRepository listProductRepository;

    private final MessageSource messageSource;

    public ListProductServiceImpl(ListRepository listRepository, ProductRepository productRepository, ListProductRepository listProductRepository, MessageSource messageSource) {
        this.listRepository = listRepository;
        this.productRepository = productRepository;
        this.listProductRepository = listProductRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean existsListProductByListProductId(long houseId, short listId, int productId) throws EntityException {
        return listProductRepository.existsById(new ListProductId(houseId, listId, productId));
    }

    @Override
    public ListProduct getListProductByListProductId(long houseId, short listId, int productId, Locale locale) throws EntityException {
        return listProductRepository
                .findById(new ListProductId(houseId, listId, productId))
                .orElseThrow(() -> new EntityException(String.format("Product with ID %d does not exist in the list with ID %d in the house with ID %d.", productId, listId, houseId), messageSource.getMessage("product_Id_Not_Exist", new Object[]{productId, listId, houseId}, locale)));
    }

    @Transactional
    @Override
    public List<ListProduct> getListProductsByListId(long houseId, short listId, Locale locale) throws EntityException, EntityNotFoundException {
        checkListId(new ListId(houseId, listId), locale);
        return listProductRepository.findAllById_HouseIdAndId_ListId(houseId, listId);
    }

    @Transactional
    @Override
    public ListProduct addListProduct(long houseId, short listId, Integer productId, String brand, Short quantity, Locale locale) throws EntityException, EntityAlreadyExistsException {
        if (listProductRepository.existsById(new ListProductId(houseId, listId, productId)))
            throw new EntityAlreadyExistsException("The product already exist.", messageSource.getMessage("product_Already_Exist", null, locale));
        ListProduct listProduct = new ListProduct(houseId, listId, productId, brand, quantity);
        return listProductRepository.save(listProduct);
    }

    @Transactional
    @Override
    public ListProduct associateListProduct(long houseId, short listId, Integer productId, String brand, Short quantity, Locale locale) throws EntityException, EntityNotFoundException {
        checkListId(new ListId(houseId, listId), locale);
        checkProductId(productId, locale);
        ListProduct listProduct = new ListProduct(houseId, listId, productId, brand, quantity);
        return listProductRepository.save(listProduct);
    }

    @Transactional
    @Override
    public void deleteListProductByListProductId(long houseId, short listId, int productId, Locale locale) throws EntityException, EntityNotFoundException {
        ListProductId id = new ListProductId(houseId, listId, productId);
        checkListProductId(id, locale);
        listProductRepository.deleteById(id);
    }

    private void checkListProductId(ListProductId listProductId, Locale locale) throws EntityNotFoundException {
        if (!listProductRepository.existsById(listProductId))
            throw new EntityNotFoundException(String.format("Product Id % d does not exist.", listProductId), messageSource.getMessage("product_Id_Not_Exist", new Object[]{listProductId.getProductId(), listProductId.getListId(), listProductId.getHouseId()}, locale));
    }

    private void checkProductId(int productId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateProductId(productId);
        if (!productRepository.existsById(productId))
            throw new EntityNotFoundException("Product does not exist.", messageSource.getMessage("product_Not_Exist", null, locale));
    }

    private void checkListId(ListId listId, Locale locale) throws EntityNotFoundException {
        if (!listRepository.existsById(listId))
            throw new EntityNotFoundException("List does not exist.", messageSource.getMessage("list_Not_Exist", null, locale));
    }
}
