package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.CategoryService;
import pt.isel.ps.gis.dal.repositories.CategoryRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final MessageSource messageSource;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MessageSource messageSource) {
        this.categoryRepository = categoryRepository;
        this.messageSource = messageSource;
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
    public List<Category> getCategoriesFiltered(CategoryFilters filters) throws EntityException {
        String name = filters.name;
        ValidationsUtils.validateCategoryName(name);
        return categoryRepository.findCategoriesByName(name);
    }

    @Override
    public Category getCategoryByCategoryId(int categoryId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateCategoryId(categoryId);
        return categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("category_Not_Exist", null, locale)));
    }
}
