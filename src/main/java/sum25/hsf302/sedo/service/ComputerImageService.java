package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.ComputerImage;

import java.util.List;

public interface ComputerImageService {
    ComputerImage save(ComputerImage image);
    List<ComputerImage> findByComputerId(Long computerId);
    ComputerImage findPrimaryImageByComputerId(Long computerId);
    void deleteByComputerId(Long computerId);
    void deleteById(Long id);

    boolean existsByComputerAndImageUrl(ComputerDevice computer, String imageUrl);

}

