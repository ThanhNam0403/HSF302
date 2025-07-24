package sum25.hsf302.sedo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.hsf302.sedo.pojo.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);
    void deleteByOrderId(Long id);
}
