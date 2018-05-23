package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.CategoryService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.model.outputModel.CategoriesOutputModel;
import pt.isel.ps.gis.model.outputModel.CategoryOutputModel;

import java.util.List;
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
    public ResponseEntity<CategoriesOutputModel> getCategories(
            @RequestParam(value = "name", required = false) String name
    ) {
        List<Category> categories;
        if (name == null)
            categories = categoryService.getCategories();
        else
            categories = categoryService.getCategoriesFiltered(new CategoryService.CategoryFilters(name));
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new CategoriesOutputModel(categories), setSirenContentType(headers),
                HttpStatus.OK);
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
