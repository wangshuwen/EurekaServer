package com.cst.xinhe.userlogin.userlogin.vo;


import java.util.Date;

/**
 * @ClassName UserLoginVOResp
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/4 9:05
 * @Vserion v0.0.1
 */

public class UserLoginVOResp {

    private Integer userId;

    private String userName;

    private String account;

    private String nickName;

    private String headimg;

    private String phone;

    private Date lastLoginTime;

    private String token;




    public UserLoginVOResp() {
    }

    public UserLoginVOResp(Integer userId, String userName, String account, String nickName, String headimg, String phone, Date lastLoginTime, String token) {
        this.userId = userId;
        this.userName = userName;
        this.account = account;
        this.nickName = nickName;
        this.headimg = headimg;
        this.phone = phone;
        this.lastLoginTime = lastLoginTime;
        this.token = token;

    }

    @Override
    public String toString() {
        return "UserLoginVOResp{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headimg='" + headimg + '\'' +
                ", phone='" + phone + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", token='" + token + '\'' +

                '}';
    }


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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
