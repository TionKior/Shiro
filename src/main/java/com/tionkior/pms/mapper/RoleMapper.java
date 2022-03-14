package com.tionkior.pms.mapper;

import com.tionkior.pms.entity.Role;
import com.tionkior.pms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author : TionKior
 * @date : 2022/3/12 16:40
 */

@Mapper
public interface RoleMapper {

    @Select("select role.* from role\n" +
            "left join user_role ur on role.role_id = ur.role_id\n" +
            "left join user u on u.user_id = ur.user_id\n" +
            "where user_name = #{userName}")
    Role getRoleByUserName(User user);

}
