package com.tionkior.pms.mapper;

import com.tionkior.pms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author : TionKior
 * @date : 2022/3/12 16:39
 */

@Mapper
public interface UserMapper {

    @Select("select * from user where user_name = #{userName}")
    User getOneUserInfo(User user);
}
