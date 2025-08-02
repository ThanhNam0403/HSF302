package sum25.hsf302.sedo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.hsf302.sedo.pojo.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentCategoryIsNull();
    List<Category> findByParentCategoryId(Long parentId);
}
