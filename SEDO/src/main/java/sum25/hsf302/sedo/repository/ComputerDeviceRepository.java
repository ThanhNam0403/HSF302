package sum25.hsf302.sedo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sum25.hsf302.sedo.pojo.ComputerDevice;

import java.util.List;

public interface ComputerDeviceRepository extends JpaRepository<ComputerDevice, Long> {
    List<ComputerDevice> findByCategoryId(Long categoryId);
    List<ComputerDevice> findTop8ByIsActiveTrueOrderByCreatedAtDesc();
    List<ComputerDevice> findByNameContainingOrDescriptionContaining(String name, String description);
    List<ComputerDevice> findByBrand(String brand);
    Page<ComputerDevice> findByIsActiveTrue(Pageable pageable);
}
