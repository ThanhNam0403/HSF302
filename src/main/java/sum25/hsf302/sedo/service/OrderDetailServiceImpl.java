package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.Order;
import sum25.hsf302.sedo.pojo.OrderDetail;
import sum25.hsf302.sedo.repository.OrderDetailRepository;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findByOrder(Order Order) {
        return orderDetailRepository.findByOrder(Order);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public void save(OrderDetail detail) {
        orderDetailRepository.save(detail);
    }
}

