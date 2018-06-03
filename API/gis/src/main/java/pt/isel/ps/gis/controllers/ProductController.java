package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.CategoryService;
import pt.isel.ps.gis.bll.ProductService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.outputModel.ProductOutputModel;
import pt.isel.ps.gis.model.outputModel.ProductsCategoryOutputModel;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/categories/{category-id}/products")
public class ProductController {

    private static final String CATEGORY_NOT_EXIST = "Category does not exist.";

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<ProductsCategoryOutputModel> getProducts(
            @PathVariable("category-id") int categoryId,
            @RequestParam(value = "name", required = false) String name
    ) throws BadRequestException, NotFoundException {
        checkCategory(categoryId);
        List<Product> products;
        try {
            if (name == null)
                products = productService.getProductsByCategoryId(categoryId);
            else
                products = productService.getProductsByCategoryIdFiltered(categoryId, new ProductService.ProductFilters(name));
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ProductsCategoryOutputModel(categoryId, products),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductOutputModel> getProduct(
            @PathVariable("category-id") int categoryId,
            @PathVariable("product-id") int productId
    ) throws NotFoundException, BadRequestException {
        Product product;
        try {
            product = productService.getProductByProductId(productId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ProductOutputModel(product), setSirenContentType(headers), HttpStatus.OK);
    }

    private void checkCategory(int categoryId) throws BadRequestException {
        try {
            if (!categoryService.existsCategoryByCategoryId(categoryId))
                throw new BadRequestException(CATEGORY_NOT_EXIST);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
