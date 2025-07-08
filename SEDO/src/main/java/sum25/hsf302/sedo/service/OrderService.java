package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrdersByCustomerId(Long customerId);
    List<Order> getAllOrders();
    Order updateOrderStatus(Long orderId);
}