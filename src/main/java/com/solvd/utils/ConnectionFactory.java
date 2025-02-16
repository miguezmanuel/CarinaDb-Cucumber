package com.solvd.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    static {
        try {
            initialize();
        } catch (Exception e) {
            LOGGER.severe("Error initializing MyBatis: " + e.getMessage());
        }
    }

    private static void initialize() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try {
                initialize();
            } catch (IOException e) {
                throw new RuntimeException("Failed to reinitialize MyBatis: " + e.getMessage(), e);
            }
        }
        return sqlSessionFactory;
    }
}
