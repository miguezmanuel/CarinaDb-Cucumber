package com.solvd.mappers;

import com.solvd.models.UserOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface OrderMapper {
    @Select("SELECT * FROM UserOrders WHERE user_id = #{userId}")
    List<UserOrder> getOrdersByUserId(@Param("userId") int userId);
}