package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sum25.hsf302.sedo.file_enum.OrderStatus;
import sum25.hsf302.sedo.pojo.Order;
import sum25.hsf302.sedo.repository.OrderDetailRepository;
import sum25.hsf302.sedo.repository.OrderRepository;
import sum25.hsf302.sedo.service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public OrderDetailRepository orderDetailRepository;

    // Lấy tất cả đơn hàng (có phân trang)
    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> searchAndFilterOrders(String search, OrderStatus status, Pageable pageable) {
        if ((search != null && !search.isEmpty()) && status != null) {
            return orderRepository.findByCustomerFullNameContainingIgnoreCaseAndStatus(search, status, pageable);
        } else if (search != null && !search.isEmpty()) {
            return orderRepository.findByCustomerFullNameContainingIgnoreCase(search, pageable);
        } else if (status != null) {
            return orderRepository.findByStatus(status, pageable);
        } else {
            return orderRepository.findAll(pageable);
        }
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderDetailRepository.deleteByOrderId(order.getId()); // Xóa tất cả OrderDetail trước
        orderRepository.delete(order); // Sau đó xóa Order
    }

    @Override
    public List<OrderStatus> getAllStatuses() {
        return Arrays.asList(OrderStatus.values());
    }

    @Override
    public Double sumTotalAmountByStatus(OrderStatus status) {
        return orderRepository.sumTotalAmountByStatus(status);
    }

    @Override
    public Page<Order> findAllByOrderByCreatedAtDesc(int page, int size) {
        return orderRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    @Override
    public Long countAllByStatusEquals(OrderStatus status) {
        return orderRepository.countAllByStatusEquals(status);
    }

    @Override
    public Long countAll() {
        return orderRepository.count();
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrderStatusById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (order.getStatus() == OrderStatus.CONFIRM) {
            order.setStatus(OrderStatus.PENDING);
        } else if (order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.DELIVERY);
        }
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}