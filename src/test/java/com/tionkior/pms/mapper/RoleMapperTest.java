package com.tionkior.pms.mapper;

import com.tionkior.pms.entity.Role;
import com.tionkior.pms.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void getRoleByUserNameTest() {
        User user = new User();
        user.setUserName("ssx");
        Role role = roleMapper.getRoleByUserName(user);
        System.out.println(role);
    }
}