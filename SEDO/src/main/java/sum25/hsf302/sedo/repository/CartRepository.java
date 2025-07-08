package sum25.hsf302.sedo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.hsf302.sedo.pojo.Cart;
import sum25.hsf302.sedo.pojo.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // In CartRepository.java
    Optional<Cart> findByUser(User user);

    // Additional query methods can be defined here if needed

}
