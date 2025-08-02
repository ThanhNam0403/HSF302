package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.ComputerImage;
import sum25.hsf302.sedo.repository.ComputerImageRepository;

import java.util.List;

@Service
public class ComputerImageServiceImpl implements ComputerImageService {

    @Autowired
    private ComputerImageRepository computerImageRepository;

    @Override
    public ComputerImage save(ComputerImage image) {
        return computerImageRepository.save(image);
    }

    @Override
    public List<ComputerImage> findByComputerId(Long computerId) {
        return computerImageRepository.findByComputerId(computerId);
    }

    @Override
    public ComputerImage findPrimaryImageByComputerId(Long computerId) {
        return computerImageRepository.findByComputerIdAndIsPrimaryTrue(computerId);
    }

    @Override
    public void deleteByComputerId(Long computerId) {
        computerImageRepository.deleteByComputerId(computerId);
    }

    @Override
    public void deleteById(Long id) {
        computerImageRepository.deleteById(id);
    }

    @Override
    public boolean existsByComputerAndImageUrl(ComputerDevice computer, String imageUrl) {
        return computerImageRepository.existsByComputerAndImageUrl(computer, imageUrl);
    }
}

