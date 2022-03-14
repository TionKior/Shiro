package com.tionkior.pms.shiro.realm;

import com.tionkior.pms.entity.Permission;
import com.tionkior.pms.entity.Role;
import com.tionkior.pms.entity.User;
import com.tionkior.pms.mapper.PermissionMapper;
import com.tionkior.pms.mapper.RoleMapper;
import com.tionkior.pms.mapper.UserMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TionKior
 */
public class UserRealm extends AuthorizingRealm {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 执行授权逻辑：因为设计每个用户只有一个角色，所以roleMapper.getRoleByUserName(user)返回值只有一个实体。
     *
     * @author Innocence
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("自定义UserRealm执行授权逻辑开始==================");
        User user = (User) principalCollection.getPrimaryPrincipal();
        if (ObjectUtils.isNotEmpty(user)) {
            List<String> roleLists = new ArrayList<>();
            List<String> permissionLists = new ArrayList<>();
            Role role = roleMapper.getRoleByUserName(user);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            roleLists.add(role.getRoleName());
            info.addRoles(roleLists);
            List<Permission> permissionByRoleId = permissionMapper.getPermissionByRoleId(role);
            for (Permission permission : permissionByRoleId) {
                permissionLists.add(permission.getPerPermission());
                info.addStringPermissions(permissionLists);
            }
            return info;
        }
        return null;
    }

    /**
     * 执行认证逻辑
     *
     * @author Innocence
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("自定义UserRealm执行认证逻辑开始==================");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        char[] password = token.getPassword();
        String pass = String.valueOf(password);
        User user = new User();
        user.setUserName(username);
        user.setUserPassword(pass);
        User userInfo = userMapper.getOneUserInfo(user);
        if (null == userInfo) {
            //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
            return null;
        }
        return new SimpleAuthenticationInfo(
                //这里的第一个参数，可以是查询到的用户实体，也可以是用户名。主要是为方便后期Subject的getPrincipal()方法取值。放进去是什么，getPrincipal()取到的就是什么。
                userInfo,
                //这里的密码，一定是查询到的实体密码，不是参数传递的密码
                userInfo.getUserPassword(),
                //salt=userName+salt
                ByteSource.Util.bytes(userInfo.getUserName() + "salt"),
                getName()
        );
    }
}