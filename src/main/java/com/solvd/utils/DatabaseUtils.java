package com.solvd.utils;

import com.solvd.mappers.OrderMapper;
import com.solvd.mappers.UserMapper;
import com.solvd.models.User;
import com.solvd.models.UserOrder;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class DatabaseUtils {

    public static User getUserByUsername(String username) {
        try (SqlSession session = ConnectionFactory.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.getUserByUsername(username);

            if (user == null) {
                throw new RuntimeException("User not found in database: " + username);
            }

            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user from database: " + e.getMessage(), e);
        }
    }

    public static List<UserOrder> getOrdersByUserId(int userId) {
        try (SqlSession session = ConnectionFactory.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            List<UserOrder> orders = orderMapper.getOrdersByUserId(userId);

            if (orders == null || orders.isEmpty()) {
                throw new RuntimeException("No orders found for user ID: " + userId);
            }

            return orders;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching orders from database: " + e.getMessage(), e);
        }
    }
}
