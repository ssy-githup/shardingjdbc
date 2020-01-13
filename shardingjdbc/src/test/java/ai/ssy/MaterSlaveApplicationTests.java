package ai.ssy;

import ai.ssy.config.util.LogUtil;
import ai.ssy.model.Order;
import ai.ssy.model.OrderGenerator;
import ai.ssy.service.OrderService;
import io.shardingsphere.api.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcApp.class)
public class MaterSlaveApplicationTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        Order order = new Order();
        order.setId(200);
        order.setOrderId(100000);
        order.setUserId(20000);
        orderService.saveOrder(order);

        Order orderById = orderService.findOrderById(200);
        System.out.println(orderById.getUserId());
        System.out.println(orderById.getOrderId());
        LogUtil.info("查询订单为={}",orderById);
    }
}
