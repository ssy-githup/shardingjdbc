package ai.ssy.service;


import ai.ssy.model.Order;
import ai.ssy.model.OrderItem;

import java.util.List;

public interface OrderService {


    public void save(Order order, OrderItem item);

}
