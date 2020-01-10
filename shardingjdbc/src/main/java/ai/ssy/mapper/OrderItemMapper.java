package ai.ssy.mapper;

import ai.ssy.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    public int save(OrderItem orderItem);
}
