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
public class StandardShardingApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    public void contextLoads() {
        Order order = OrderGenerator.generate();

        OrderItem orderItem = ItemGenerator.generate();

        orderService.save(order, orderItem);
    }

}

