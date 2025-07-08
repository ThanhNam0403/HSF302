package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.repository.ComputerDeviceRepository;
import java.util.List;

import static java.util.Locale.filter;

@Service
public class ComputerDeviceServiceImpl implements ComputerDeviceService {

    @Autowired
    private ComputerDeviceRepository computerDeviceRepository;


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
}