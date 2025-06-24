package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.Category;
import sum25.hsf302.sedo.repository.CategoryRepository;
import java.util.List;

import static java.util.Locale.filter;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(findById(id));
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findTopLevelCategories() {
        return categoryRepository.findByParentCategoryIsNull();
    }

    @Override
    public List<Category> findSubcategories(Long parentId) {
        return categoryRepository.findByParentCategoryId(parentId);
    }
}