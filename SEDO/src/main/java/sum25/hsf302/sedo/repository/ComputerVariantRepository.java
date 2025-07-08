package sum25.hsf302.sedo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.hsf302.sedo.pojo.ComputerVariant;

import java.util.List;

public interface ComputerVariantRepository extends JpaRepository<ComputerVariant, Long> {
    List<ComputerVariant> findByComputerId(Long computerId);
}
