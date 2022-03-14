package com.tionkior.pms.entity;

import lombok.ToString;

import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2022-03-12 14:57:02
 */
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 850639118366352627L;
    /**
     * 主键,用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 用户盐
     */
    private String userSalt;
    /**
     * 账户是否被锁定,1:锁定,0:未锁定
     */
    private String userLocked;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserLocked() {
        return userLocked;
    }

    public void setUserLocked(String userLocked) {
        this.userLocked = userLocked;
    }

}

