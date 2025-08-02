package sum25.hsf302.sedo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sum25.hsf302.sedo.pojo.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    int countByIsActiveTrue();

    Page<User> findByFullNameLike(String fullName, Pageable pageable);
}