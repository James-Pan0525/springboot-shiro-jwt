package com.pwl.shiro.entity;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author pwl
 * @since 2019-06-20
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String userName;
    private String fullName;
    private String password;
    private String salt;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        ", userId=" + userId +
        ", userName=" + userName +
        ", fullName=" + fullName +
        ", password=" + password +
        ", salt=" + salt +
        "}";
    }
}
