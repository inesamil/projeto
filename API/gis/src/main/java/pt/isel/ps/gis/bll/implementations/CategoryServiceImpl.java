package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.CategoryService;
import pt.isel.ps.gis.dal.repositories.CategoryRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public List<Category> getCategoriesFiltered(CategoryFilters categoryFilters) {
        ArrayList<Category> categories = new ArrayList<>();
        categoryRepository.findCategoriesByName(categoryFilters.name).forEach(categories::add);
        return categories;
    }

    @Override
    public Optional<Category> getCategoryByCategoryId(int categoryId) throws EntityException {
        ValidationsUtils.validateCategoryId(categoryId);
        return categoryRepository.findById(categoryId);
    }
}
