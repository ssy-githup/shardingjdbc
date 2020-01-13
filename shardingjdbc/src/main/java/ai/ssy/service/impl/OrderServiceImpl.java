package ai.ssy.service.impl;

import ai.ssy.mapper.OrderItemMapper;
import ai.ssy.mapper.OrderMapper;
import ai.ssy.model.Order;
import ai.ssy.model.OrderItem;
import ai.ssy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public void save(Order order, OrderItem item) {
        orderMapper.save(order);
        orderItemMapper.save(item);
    }

    @Override
    public List<Order> queryOrderByHint() {
        return orderMapper.queryOrderByHint();
    }

}
