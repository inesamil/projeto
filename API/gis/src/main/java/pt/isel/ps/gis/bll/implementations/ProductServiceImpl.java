package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.ProductService;
import pt.isel.ps.gis.dal.repositories.ProductRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean existsProductByProductId(int productId) throws EntityException {
        ValidationsUtils.validateProductId(productId);
        return productRepository.existsById(productId);
    }

    @Override
    public Optional<Product> getProductByProductId(int productId) throws EntityException {
        ValidationsUtils.validateProductId(productId);
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) throws EntityException {
        ValidationsUtils.validateCategoryId(categoryId);
        return productRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByCategoryIdFiltered(int categoryId, ProductFilters filters) throws EntityException {
        ValidationsUtils.validateCategoryId(categoryId);
        return productRepository.findProductsByNameAndCategoryId(categoryId, filters.name);
    }

    @Override
    public Product addProduct(Product product) throws EntityException {
        if (product.getProductId() != null && productRepository.existsById(product.getProductId()))
            throw new EntityException(String.format("Product with ID %d already exists.",
                    product.getProductId()));
        return productRepository.save(product);
    }
}
