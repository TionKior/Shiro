package com.tionkior.pms.controller;

import com.tionkior.pms.entity.User;
import com.tionkior.pms.entity.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : TionKior
 * @date : 2022/3/12 17:34
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/login")
    public Result userLogin(@RequestBody User user) {

        // 加密方式   MD5盐 盐值:ByteSource.Util.bytes(UserName + salt) 二次散列 Hex密码加密编码
        System.out.println(new Md5Hash("123", ByteSource.Util.bytes("ssxsalt"), 2).toHex());


        log.info("用户登陆请求到达");
        // 登录失败从request中获取shiro处理的异常信息
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getUserPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            // 从UserRealm里返回的SimpleAuthenticationInfo获取到认证成功的用户名，
            // subject.getPrincipal()获取的是SimpleAuthenticationInfo设置的第一个参数
            User loginUser = (User) subject.getPrincipal();
            Session session = subject.getSession();
            session.setAttribute("loginUser", loginUser);
            // SessionCache.sessionCache.put((String)subject.getSession().getId(),session);
            loginUser.setUserPassword("");

            // 返回成功信息
            Map<String, Object> map = new HashMap<>(10);
            map.put("loginUser", loginUser);
            map.put("token", subject.getSession().getId());
            return Result.success(map);
        } catch (IncorrectCredentialsException e) {
            return Result.failure("0001", "密码错误");
        } catch (LockedAccountException e) {
            return Result.failure("0002", "账户被冻结");
        } catch (AuthenticationException e) {
            return Result.failure("0003", "账户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

}