package sum25.hsf302.sedo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sum25.hsf302.sedo.pojo.Cart;
import sum25.hsf302.sedo.pojo.CartDetail;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    Optional<CartDetail> findByCartAndComputer(Cart cart, ComputerDevice computer);

    void deleteByCart(Cart cart);

    List<CartDetail> findByCart(Cart cart);

    List<CartDetail> findAllByCart(Cart cart);

    void deleteByComputer(ComputerDevice computer);

    void deleteByIdInAndCartUser(List<Long> selectedIds, User user);
}