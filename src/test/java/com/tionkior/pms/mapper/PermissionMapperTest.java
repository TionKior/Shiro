package com.tionkior.pms.mapper;

import com.tionkior.pms.entity.Permission;
import com.tionkior.pms.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PermissionMapperTest {

    @Autowired
    private PermissionMapper mapper;

    @Test
    void getPermissionByRoleIdTest() {
        Role role = new Role();
        role.setRoleId(2);
        List<Permission> permissionByRoleId = mapper.getPermissionByRoleId(role);
        for (Permission permission : permissionByRoleId) {
            System.out.println(permission);
        }
    }

}