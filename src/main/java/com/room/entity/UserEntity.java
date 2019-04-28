package com.room.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 */
public class UserEntity implements Serializable {

    private Integer userId;//用户id

    private String userName;//用户名

    private String wxId;//微信用户唯一的unionID

    private String password;//密码

    private String email;//邮箱

    private String  logindate;//用户登陆时间

    private String salt;

    private Integer deleted;//删除状态(0：有效 1：无效)

    private Integer role;//角色(0：管理员 1：普通)

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getRole() {
        return role;
    }

    public Integer getLoginState() {
        return loginState;
    }

    public void setLoginState(Integer loginState) {
        this.loginState = loginState;
    }

    private Integer loginState;//登录状态(0:未登录，1:已登录)

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getWxId() {
        return wxId;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getLogindate() {
        return logindate;
    }

    public void setLogindate(String logindate) {
        this.logindate = logindate;
    }
}
