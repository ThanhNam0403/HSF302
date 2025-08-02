package sum25.hsf302.sedo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.ComputerImage;

import java.util.List;

public interface ComputerImageRepository extends JpaRepository<ComputerImage, Long> {

    List<ComputerImage> findByComputerId(Long computerId);

    ComputerImage findByComputerIdAndIsPrimaryTrue(Long computerId);

    void deleteByComputerId(Long computerId);
    boolean existsByComputerAndImageUrl(ComputerDevice computer, String imageUrl);

}
