package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.CategoryService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.model.outputModel.CategoriesOutputModel;
import pt.isel.ps.gis.model.outputModel.CategoryOutputModel;

import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<CategoriesOutputModel> getCategories() {
        // categoryService.getCategoriesFiltered();
        // TODO qual o getCategories a usar
        return null;
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryOutputModel> getCategory(@PathVariable("category-id") int categoryId)
            throws EntityException, NotFoundException {
        Optional<Category> categoryOptional = categoryService.getCategoryByCategoryId(categoryId);
        Category category = categoryOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new CategoryOutputModel(category), setSirenContentType(headers), HttpStatus.OK);
    }
}
