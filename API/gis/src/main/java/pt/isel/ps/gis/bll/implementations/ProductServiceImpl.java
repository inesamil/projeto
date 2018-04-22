package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.ProductService;
import pt.isel.ps.gis.dal.repositories.ProductRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.ProductId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean existsProductByProductId(int categoryId, int productId) throws EntityException {
        return productRepository.existsById(new ProductId(categoryId, productId));
    }

    @Override
    public Optional<Product> getProductByProductId(int categoryId, int productId) throws EntityException {
        return productRepository.findById(new ProductId(categoryId, productId));
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) throws EntityException {
        ValidationsUtils.validateCategoryId(categoryId);
        return productRepository.findAllById_CategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByCategoryIdFiltered(int categoryId, ProductFilters filters) throws EntityException {
        ValidationsUtils.validateCategoryId(categoryId);
        return productRepository.findProductsByNameAndCategoryId(categoryId, filters.name);
    }

    @Override
    public Product addProduct(Product product) throws EntityException {
        //TODO: devemos ver se já existe ?
        if (productRepository.existsById(product.getId()))
            throw new EntityException(String.format("Product with ID %d in the category with ID %d already exists.",
                    product.getId().getProductId(), product.getId().getCategoryId()));
        return productRepository.insertProduct(product);
    }


}
