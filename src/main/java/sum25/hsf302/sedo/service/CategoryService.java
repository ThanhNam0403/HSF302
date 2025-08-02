package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.Category;
import java.util.List;

public interface CategoryService {
    Category save(Category category);
    Category findById(Long id);
    List<Category> findAll();
    void delete(Long id);
    Category update(Category category);
    List<Category> findTopLevelCategories();
    List<Category> findSubcategories(Long parentId);
}