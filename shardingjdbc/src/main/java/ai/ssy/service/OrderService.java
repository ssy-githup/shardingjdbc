package ai.ssy.service;


import ai.ssy.model.Order;
import ai.ssy.model.OrderItem;

import java.util.List;


public interface OrderService {

    public void save(Order order, OrderItem item);

    /***
    * @FunctionName:
    * @Description: 强制路由分片
    * @author: ssy
    * @date:
    */
    public List<Order> queryOrderByHint();

    Integer saveOrder(Order order);

    Order findOrderById(int i);
}
