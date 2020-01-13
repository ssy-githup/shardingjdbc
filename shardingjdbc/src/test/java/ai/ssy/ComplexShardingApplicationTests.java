package ai.ssy;

import ai.ssy.model.ItemGenerator;
import ai.ssy.model.Order;
import ai.ssy.model.OrderGenerator;
import ai.ssy.model.OrderItem;
import ai.ssy.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcApp.class)
public class ComplexShardingApplicationTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        Order order = OrderGenerator.generate();
        //指定order的订单ID
        order.setUserId(10000013);
        order.setOrderId(1000010);
        OrderItem orderItem = ItemGenerator.generate();
        orderItem.setUserId(order.getUserId());
        orderItem.setOrderId(order.getOrderId());
        orderService.save(order, orderItem);
    }
}
