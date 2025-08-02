package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.Order;
import sum25.hsf302.sedo.pojo.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findByOrderId(Long orderId);
    void save(OrderDetail detail);
    List<OrderDetail> findByOrder(Order order);
}

