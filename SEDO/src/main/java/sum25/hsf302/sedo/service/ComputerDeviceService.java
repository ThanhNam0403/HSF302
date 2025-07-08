package sum25.hsf302.sedo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import java.util.List;

public interface ComputerDeviceService {
    ComputerDevice save(ComputerDevice device);
    ComputerDevice findById(Long id);
    List<ComputerDevice> findAll();
    void delete(Long id);
    ComputerDevice update(ComputerDevice device);
    List<ComputerDevice> findByCategory(Long categoryId);
    List<ComputerDevice> findFeaturedProducts();
    List<ComputerDevice> searchProducts(String keyword);
    List<ComputerDevice> findByBrand(String brand);
    boolean updateStock(Long id, int quantity);
    Page<ComputerDevice> findFeaturedProductsPaginated(Pageable pageable);
}