package pt.isel.ps.gis.bll.implementations;

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

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean existsProductByProductId(int productId) throws EntityException {
        ValidationsUtils.validateProductId(productId);
        return productRepository.existsById(productId);
    }

    @Override
    public Product getProductByCategoryIdAndProductId(int categoryId, int productId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateCategoryId(categoryId);
        ValidationsUtils.validateProductId(productId);
        return productRepository
                .findByCategoryIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("The product with ID %d does not exist in category with ID %d.",
                        productId, categoryId)));
    }

    @Transactional
    @Override
    public List<Product> getProductsByCategoryId(int categoryId) throws EntityException, EntityNotFoundException {
        checkCategoryId(categoryId);
        return productRepository.findAllByCategoryId(categoryId);
    }

    @Transactional
    @Override
    public List<Product> getProductsByCategoryIdFiltered(int categoryId, ProductFilters filters) throws EntityException, EntityNotFoundException {
        checkCategoryId(categoryId);
        ValidationsUtils.validateProductName(filters.name);
        return productRepository.findProductsByNameAndCategoryId(categoryId, filters.name);
    }

    /* @Override
    public Product addProduct(int categoryId, String productName, boolean productEdible, short productShelflife, String productShelflifeTimeunit) throws EntityException {
        return productRepository.save(new Product(categoryId, productName, productEdible, productShelflife, productShelflifeTimeunit));
    } */

    private void checkCategoryId(int categoryId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateCategoryId(categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(String.format("The category with ID %d does not exist.",
                    categoryId));
        }
    }
}
