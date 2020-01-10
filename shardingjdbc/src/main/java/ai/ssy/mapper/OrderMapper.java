package ai.ssy.mapper;

import ai.ssy.model.Order;
import ai.ssy.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    public int save(Order order);

}
