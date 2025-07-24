package sum25.hsf302.sedo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sum25.hsf302.sedo.file_enum.OrderStatus;
import sum25.hsf302.sedo.pojo.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrdersByCustomerId(Long customerId);
    List<Order> getAllOrders();
    Order updateOrderStatusById(Long orderId);

    Order getOrderById(Long orderId);

    Long countAll();
    Long countAllByStatusEquals(OrderStatus status);
    Double sumTotalAmountByStatus(OrderStatus status);
    Page<Order> findAllByOrderByCreatedAtDesc(int page, int size);

    Page<Order> findAll(Pageable pageable);

    Page<Order> searchAndFilterOrders(String search, OrderStatus status, Pageable pageable);

    Order findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void delete(Long id);

    List<OrderStatus> getAllStatuses();
}