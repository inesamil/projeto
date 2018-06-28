package pt.isel.ps.gis.controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.ProductService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.outputModel.ProductOutputModel;
import pt.isel.ps.gis.model.outputModel.ProductsCategoryOutputModel;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/categories/{category-id}/products")
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    public ProductController(ProductService productService, MessageSource messageSource) {
        this.productService = productService;
        this.messageSource = messageSource;
    }

    @GetMapping("")
    public ResponseEntity<ProductsCategoryOutputModel> getProducts(
            @PathVariable("category-id") int categoryId,
            @RequestParam(value = "name", required = false) String name,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<Product> products;
        try {
            if (name == null)
                products = productService.getProductsByCategoryId(categoryId, locale);
            else
                products = productService.getProductsByCategoryIdFiltered(categoryId, new ProductService.ProductFilters(name), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ProductsCategoryOutputModel(categoryId, products),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductOutputModel> getProduct(
            @PathVariable("category-id") int categoryId,
            @PathVariable("product-id") int productId,
            Locale locale
    ) throws NotFoundException, BadRequestException {
        Product product;
        try {
            product = productService.getProductByCategoryIdAndProductId(categoryId, productId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ProductOutputModel(product), setSirenContentType(headers), HttpStatus.OK);
    }
}
