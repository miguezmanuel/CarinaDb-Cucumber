package com.solvd.dbtest;

import com.solvd.mappers.UserMapper;
import com.solvd.models.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class TestDbConnection {
    public static void main(String[] args) {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            try (SqlSession session = sqlSessionFactory.openSession()) {
                UserMapper userMapper = session.getMapper(UserMapper.class);
                User user = userMapper.getUserByUsername("standard_user");

                if (user != null) {
                    System.out.println("Found user: " + user.getUsername());
                } else {
                    System.out.println("User not found :(");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
