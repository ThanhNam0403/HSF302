package sum25.hsf302.sedo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sum25.hsf302.sedo.file_enum.OrderStatus;
import sum25.hsf302.sedo.pojo.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    Long countAllByStatusEquals(OrderStatus status);
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = :status")
    Double sumTotalAmountByStatus(@Param("status") OrderStatus status);
    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Order> findByCustomerFullNameContainingIgnoreCase(String fullName, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByCustomerFullNameContainingIgnoreCaseAndStatus(String fullName, OrderStatus status, Pageable pageable);

}