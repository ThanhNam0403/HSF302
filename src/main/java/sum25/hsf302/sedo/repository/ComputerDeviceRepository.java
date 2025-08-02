package sum25.hsf302.sedo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sum25.hsf302.sedo.pojo.Category;
import sum25.hsf302.sedo.pojo.ComputerDevice;

import java.util.List;
@Repository
public interface ComputerDeviceRepository extends JpaRepository<ComputerDevice, Long> {
    List<ComputerDevice> findByCategoryId(Long categoryId);
    List<ComputerDevice> findTop8ByIsActiveTrueOrderByCreatedAtDesc();
    List<ComputerDevice> findByNameContainingOrDescriptionContaining(String name, String description);
    List<ComputerDevice> findByBrand(String brand);
    Page<ComputerDevice> findByIsActiveTrue(Pageable pageable);

    Page<ComputerDevice> findByCategory(Category category, Pageable pageable);

    Page<ComputerDevice> findByCategory_NameAndNameContainingIgnoreCase(String category, String name, Pageable pageable);


    ComputerDevice findByName(String name);

    Page<ComputerDevice> findByCategory_Name(String categoryName, Pageable pageable);

    List<ComputerDevice> findByCategory_NameIgnoreCaseAndBrandIgnoreCase(String categoryName, String brand);
    int countAllByIsActiveTrue();

    // Thêm method này nếu muốn search + paging
    Page<ComputerDevice> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable);

}



