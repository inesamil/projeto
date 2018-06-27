package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.ProductService;
import pt.isel.ps.gis.dal.repositories.CategoryRepository;
import pt.isel.ps.gis.dal.repositories.ProductRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final MessageSource messageSource;

    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, MessageSource messageSource) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean existsProductByProductId(int productId) throws EntityException {
        ValidationsUtils.validateProductId(productId);
        return productRepository.existsById(productId);
    }

    @Override
    public Product getProductByCategoryIdAndProductId(int categoryId, int productId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateCategoryId(categoryId);
        ValidationsUtils.validateProductId(productId);
        return productRepository
                .findByCategoryIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("product_Id_In_Category_Not_Exist", new Object[]{productId, categoryId}, locale)));
    }

    @Transactional
    @Override
    public List<Product> getProductsByCategoryId(int categoryId, Locale locale) throws EntityException, EntityNotFoundException {
        checkCategoryId(categoryId, locale);
        return productRepository.findAllByCategoryId(categoryId);
    }

    @Transactional
    @Override
    public List<Product> getProductsByCategoryIdFiltered(int categoryId, ProductFilters filters, Locale locale) throws EntityException, EntityNotFoundException {
        checkCategoryId(categoryId, locale);
        ValidationsUtils.validateProductName(filters.name);
        return productRepository.findProductsByNameAndCategoryId(categoryId, filters.name);
    }

    /* @Override
    public Product addProduct(int categoryId, String productName, boolean productEdible, short productShelflife, String productShelflifeTimeunit) throws EntityException {
        return productRepository.save(new Product(categoryId, productName, productEdible, productShelflife, productShelflifeTimeunit));
    } */

    private void checkCategoryId(int categoryId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateCategoryId(categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(messageSource.getMessage("category_Id_Not_Exist", new Object[]{categoryId}, locale));
        }
    }
}
