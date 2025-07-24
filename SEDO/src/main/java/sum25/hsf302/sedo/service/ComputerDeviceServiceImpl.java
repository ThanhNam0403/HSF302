package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sum25.hsf302.sedo.pojo.Category;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.repository.CartDetailRepository;
import sum25.hsf302.sedo.repository.ComputerDeviceRepository;

import java.util.List;

@Service
public class ComputerDeviceServiceImpl implements ComputerDeviceService {

    @Autowired
    private ComputerDeviceRepository computerDeviceRepository;

    @Autowired(required = false) // Nếu CartDetailRepository chưa cần, bạn có thể bỏ required=false
    private CartDetailRepository cartDetailRepository;

    // Phân trang & tìm kiếm
    @Override
    public Page<ComputerDevice> searchDevices(String keyword, Pageable pageable) {
        return computerDeviceRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);
    }

    @Override
    public Page<ComputerDevice> findAll(Pageable pageable) {
        return computerDeviceRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ComputerDevice device = computerDeviceRepository.findById(id).orElse(null);
        if (device != null && cartDetailRepository != null) {
            // Xóa hết CartDetail chứa device này (nếu có repo)
            cartDetailRepository.deleteByComputer(device);
            computerDeviceRepository.delete(device);
        }
        // Có thể giữ dòng này hoặc bỏ, vì delete(device) đã xóa theo id rồi.
        computerDeviceRepository.deleteById(id);
    }

    @Override
    public int countActiveDevices() {
        return computerDeviceRepository.countAllByIsActiveTrue();
    }

    // CRUD cơ bản
    @Override
    public ComputerDevice save(ComputerDevice device) {
        return computerDeviceRepository.save(device);
    }

    @Override
    public ComputerDevice findById(Long id) {
        return computerDeviceRepository.findById(id).orElse(null);
    }

    @Override
    public List<ComputerDevice> findAll() {
        return computerDeviceRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        computerDeviceRepository.deleteById(id);
    }

    @Override
    public ComputerDevice update(ComputerDevice device) {
        return computerDeviceRepository.save(device);
    }

    // Các query custom
    @Override
    public List<ComputerDevice> findByCategory(Long categoryId) {
        return computerDeviceRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<ComputerDevice> findFeaturedProducts() {
        return computerDeviceRepository.findTop8ByIsActiveTrueOrderByCreatedAtDesc();
    }

    @Override
    public List<ComputerDevice> searchProducts(String keyword) {
        return computerDeviceRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
    }

    @Override
    public List<ComputerDevice> findByBrand(String brand) {
        return computerDeviceRepository.findByBrand(brand);
    }

    @Override
    public boolean updateStock(Long id, int quantity) {
        ComputerDevice device = findById(id);
        if (device != null && device.getStockQuantity() >= quantity) {
            device.setStockQuantity(device.getStockQuantity() - quantity);
            computerDeviceRepository.save(device);
            return true;
        }
        return false;
    }

    @Override
    public Page<ComputerDevice> findFeaturedProductsPaginated(Pageable pageable) {
        return computerDeviceRepository.findByIsActiveTrue(pageable);
    }

    @Override
    public List<ComputerDevice> getDevicesByCategoryAndBrand(String category, String brand) {
        return computerDeviceRepository.findByCategory_NameIgnoreCaseAndBrandIgnoreCase(category, brand);
    }

    @Override
    public Page<ComputerDevice> findByCategory(String categoryName, Pageable pageable) {
        return computerDeviceRepository.findByCategory_Name(categoryName, pageable);
    }

    @Override
    public ComputerDevice findByName(String name) {
        return computerDeviceRepository.findByName(name);
    }

    @Override
    public Page<ComputerDevice> findByCategory(Category category, Pageable pageable) {
        return computerDeviceRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<ComputerDevice> findByCategory_NameAndNameContainingIgnoreCase(String category, String name, Pageable pageable) {
        return computerDeviceRepository.findByCategory_NameAndNameContainingIgnoreCase(category, name, pageable);
    }

}
