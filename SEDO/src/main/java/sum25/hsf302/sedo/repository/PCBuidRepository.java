package sum25.hsf302.sedo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sum25.hsf302.sedo.pojo.PCBuild;
import sum25.hsf302.sedo.pojo.User;

import java.util.Optional;

@Repository
public interface PCBuidRepository extends JpaRepository <PCBuild, Long> {
    Optional<PCBuild> findFirstByUserOrderByCreatedAtDesc(User user);

}
