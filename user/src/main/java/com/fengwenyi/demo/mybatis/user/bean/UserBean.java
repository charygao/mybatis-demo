package com.fengwenyi.demo.mybatis.user.bean;

import java.util.Date;

/**
 * @author Wenyi Feng
 */
public class UserBean {

    private Long userId;
    private Long userAuthId;
    private String name;
    private int age;
    private String account;
    private Date regTime;
    private Date loginTime;

    public UserBean() {
    }

    public UserBean(Long userId, Long userAuthId, String name, int age,
                    String account, Date regTime, Date loginTime) {
        this.userId = userId;
        this.userAuthId = userAuthId;
        this.name = name;
        this.age = age;
        this.account = account;
        this.regTime = regTime;
        this.loginTime = loginTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserAuthId() {
        return userAuthId;
    }

    public void setUserAuthId(Long userAuthId) {
        this.userAuthId = userAuthId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", userAuthId=" + userAuthId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", account='" + account + '\'' +
                ", regTime=" + regTime +
                ", loginTime=" + loginTime +
                '}';
    }
}
