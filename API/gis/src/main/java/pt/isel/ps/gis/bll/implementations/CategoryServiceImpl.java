package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.CategoryService;
import pt.isel.ps.gis.dal.repositories.CategoryRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final static String CATEGORY_NOT_EXIST = "Category does not exist.";

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean existsCategoryByCategoryId(int categoryId) throws EntityException {
        ValidationsUtils.validateCategoryId(categoryId);
        return categoryRepository.existsById(categoryId);
    }

    @Override
    public List<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public List<Category> getCategoriesFiltered(String name) {
        CategoryFilters categoryFilters = new CategoryFilters(name);
        return categoryRepository.findCategoriesByName(categoryFilters.name);
    }

    @Override
    public Category getCategoryByCategoryId(int categoryId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateCategoryId(categoryId);
        return categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_EXIST));
    }
}
