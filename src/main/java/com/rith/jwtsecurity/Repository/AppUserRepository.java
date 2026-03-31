package com.rith.jwtsecurity.Repository;


import com.rith.jwtsecurity.Model.AppUser;
import com.rith.jwtsecurity.Model.request.AppUserRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppUserRepository {

    @Results(id = "userMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "roles", column = "user_id", many = @Many(select = "getAllRolesByUserId"))
    })
    @Select("""
        SELECT * FROM app_users WHERE email = #{email}
    """)
    AppUser getUserByEmail(String email);

    @Select("""
        SELECT name FROM app_user_role aur
        INNER JOIN app_roles ar
        ON aur.role_id = ar.role_id
        WHERE user_id = #{userId}
    """)
    List<String> getAllRolesByUserId(Long userId);

    @Select("""
                INSERT INTO app_users
                VALUES (default, #{request.fullName}, #{request.email}, #{request.password})
                RETURNING *
            """)
    @ResultMap("userMapper")
    AppUser register(@Param("request") AppUserRequest request);


    @Insert("Insert into app_user_role (user_id, role_id) values (#{userId}, #{roleId})")
    void insertUserIdAndRoleId(Long roleId, Long userId);

    @Select("""
                SELECT * FROM app_users
                WHERE user_id = #{userId}
            """)
    @ResultMap("userMapper")
    AppUser getUserById(Long userId);
}
