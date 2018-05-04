package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.ProductService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.outputModel.ProductOutputModel;
import pt.isel.ps.gis.model.outputModel.ProductsCategoryOutputModel;

import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("v1/categories/{category-id}/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<ProductsCategoryOutputModel> getProducts(@PathVariable("category-id") int categoryId) {
        // productService.getProductsByCategoryId()
        // TODO qual usar?
        return null;
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductOutputModel> getProduct(
            @PathVariable("category-id") int categoryId,
            @PathVariable("product-id") int productId) throws EntityException, NotFoundException {
        Optional<Product> productOptional = productService.getProductByProductId(categoryId, productId);
        Product product = productOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ProductOutputModel(product), setSirenContentType(headers), HttpStatus.OK);
    }
}
