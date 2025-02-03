package com.solvd.mappers;

import com.solvd.models.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * FROM User WHERE username = #{username}")
    User getUserByUsername(@Param("username") String username);
}
