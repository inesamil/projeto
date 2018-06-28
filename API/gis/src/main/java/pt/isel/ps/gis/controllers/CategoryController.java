package pt.isel.ps.gis.controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.CategoryService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.model.outputModel.CategoriesOutputModel;
import pt.isel.ps.gis.model.outputModel.CategoryOutputModel;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final MessageSource messageSource;

    public CategoryController(CategoryService categoryService, MessageSource messageSource) {
        this.categoryService = categoryService;
        this.messageSource = messageSource;
    }

    @GetMapping("")
    public ResponseEntity<CategoriesOutputModel> getCategories(
            @RequestParam(value = "name", required = false) String name,
            Locale locale
    ) throws BadRequestException {
        List<Category> categories;
        if (name == null)
            categories = categoryService.getCategories();
        else {
            try {
                CategoryService.CategoryFilters filters = new CategoryService.CategoryFilters(name);
                categories = categoryService.getCategoriesFiltered(filters);
            } catch (EntityException e) {
                throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
            }
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new CategoriesOutputModel(categories), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryOutputModel> getCategory(
            @PathVariable("category-id") int categoryId,
            Locale locale
    ) throws NotFoundException, BadRequestException {
        Category category;
        try {
            category = categoryService.getCategoryByCategoryId(categoryId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new CategoryOutputModel(category), setSirenContentType(headers), HttpStatus.OK);
    }
}
