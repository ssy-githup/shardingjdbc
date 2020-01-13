package ai.ssy.mapper;

import ai.ssy.model.Order;
import ai.ssy.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    public int save(Order order);

    List<Order> queryOrderByHint();

    @Insert("INSERT INTO t_order(id,user_id, order_id) VALUES(#{id},#{userId}, #{orderId}) ")
    Integer saveOrder(Order order);


    Order findOrderById(int i);
}
