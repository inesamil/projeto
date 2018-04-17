package pt.isel.ps.gis.bll;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.dal.repositories.CategoryRepository;
import pt.isel.ps.gis.model.Category;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Iterable<Category> getCategoriesFiltered(CategoryFilters categoryFilters) {
        return categoryRepository.getCategoriesByName(categoryFilters.name);
    }

    @Override
    public Optional<Category> getCategoryByCategoryId(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
