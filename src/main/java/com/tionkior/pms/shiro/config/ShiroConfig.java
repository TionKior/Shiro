package com.tionkior.pms.shiro.config;

import com.tionkior.pms.shiro.MySessionManager;
import com.tionkior.pms.shiro.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;
    @Value("${spring.redis.shiro.timeout}")
    private int timeout;
//    @Value("${spring.redis.shiro.password}")
//    private String password;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 创建ShrioFilterFactoryBean
     *
     * @author Innocence
     * @date 2019/9/25 002510:57
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        Map<String, String> filterMap = new LinkedHashMap<>();
        /*
         * Shiro内置过滤器，可以实现权限相关的拦截器，常用的有：
         *   anon：无需认证（登录）即可访问
         *   authc：必须认证才可以访问
         *   user：如果使用rememberme的功能可以直接访问
         *   perms：该资源必须得到资源权限才能访问
         *   role：该资源必须得到角色资源才能访问
         */
        // filterMap.put("/admin/**", "authc,roles[超级管理员]");

        //放过登录请求
        filterMap.put("/user/login", "anon");

        // 放过文件上传的接口请求
        filterMap.put("/goods/uploadFile", "anon");
        //filterMap.put("/admin/deleteOne","authc,roles[超级管理员,店长]");
        //filterMap.put("/user/getInitMenus","authc");

        // 放过swagger2
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources", "anon");
        filterMap.put("/swagger-resources/configuration/security", "anon");
        filterMap.put("/swagger-resources/configuration/ui", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/webjars/springfox-swagger-ui/**", "anon");

        // 其他资源全部拦截
        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        // 配置shiro默认登录地址,前后端分离应该由前端控制页面跳转
        shiroFilterFactoryBean.setLoginUrl("/unauth");
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     *
     * @author Innocence
     * SecurityManager 是 Shiro 架构的核心，通过它来链接Realm和用户(文档中称之为Subject.)
     * @date 2019/9/25 002510:57
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入rememberme对象
        securityManager.setRememberMeManager(cookieRememberMeManager());
        //注入session
        securityManager.setSessionManager(sessionManager());
        //注入缓存管理对象
        securityManager.setCacheManager(redisCacheManager());
        //将Realm注入SecurityManager
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 创建Realm
     * @author Innocence
     * @date 2019/9/25 002510:59
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        //设置解密规则
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        userRealm.setCachingEnabled(false);
        return userRealm;
    }

    /**
     * 因为密码是加过密的，所以，如果要Shiro验证用户身份的话，需要告诉它我们用的是md5加密的，并且是加密了两次。
     * 同时我们在自己的Realm中也通过SimpleAuthenticationInfo返回了加密时使用的盐。
     * 这样Shiro就能顺利的解密密码并验证用户名和密码是否正确了。
     *
     * @return org.apache.shiro.authc.credential.HashedCredentialsMatcher
     * @author Innocence
     * @date 2019/9/27 002716:12
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //使用MD5散列算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列两次，相当于MD5（MD5（“”））
        hashedCredentialsMatcher.setHashIterations(2);

        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 自定义sessionmanager
     *
     * @return org.apache.shiro.session.mgt.SessionManager
     * @author Innocence
     * @date 2019/10/17 001710:02
     */
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        // 取消登陆跳转URL后面的jsessionid参数
        mySessionManager.setSessionIdUrlRewritingEnabled(false);
        mySessionManager.setSessionDAO(redisSessionDAO());
        // 不过期
        mySessionManager.setGlobalSessionTimeout(-1);
        return mySessionManager;
    }

    /**
     * 配置shiro redisManager
     * 使用shiro-redis开源插件
     *
     * @return org.crazycake.shiro.RedisManager
     * @author Innocence
     * @date 2019/10/17 001710:03
     */
    @Bean(name = "redisManager")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host + ":" + port);
        redisManager.setTimeout(timeout);
//        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager的Redis实现
     *
     * @return org.crazycake.shiro.RedisCacheManager
     * @author Innocence
     * @date 2019/10/17 001710:20
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用shiro-redis开源插件
     *
     * @return org.crazycake.shiro.RedisSessionDAO
     * @author Innocence
     * @date 2019/10/17 001710:23
     */
    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 开启shiro 的aop注解支持
     *
     * @param securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author Innocence
     * @date 2019/9/27 002716:22
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cookie对象
     *
     * @return org.apache.shiro.web.servlet.SimpleCookie
     * @author Innocence
     * @date 2019/9/27 002716:24
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        logger.info("ShiroConfiguration.rememberMeCookie()=============");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象
     *
     * @return org.apache.shiro.web.mgt.CookieRememberMeManager
     * @author Innocence
     * @date 2019/9/27 002716:24
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        logger.info("ShiroConfiguration.rememberMeManager()========");
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(rememberMeCookie());
        return manager;
    }


    /**
     * 加入下面两个bean，shiro才会执行授权逻辑
     *
     * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     * @author Innocence
     * @date 2019/11/6 000616:15
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}