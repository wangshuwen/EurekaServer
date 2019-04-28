package com.cst.xinhe.persistence.vo;


import com.cst.xinhe.persistence.model.menu.SysMenu;
import com.cst.xinhe.persistence.model.role.SysRole;

import java.util.Date;
import java.util.List;

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

    //权限
    private List<SysMenu> menus;

    //角色
    private SysRole sysRole;


    public UserLoginVOResp() {
    }

    public UserLoginVOResp(Integer userId, String userName, String account, String nickName, String headimg, String phone, Date lastLoginTime, String token, List<SysMenu> menus, SysRole sysRole) {
        this.userId = userId;
        this.userName = userName;
        this.account = account;
        this.nickName = nickName;
        this.headimg = headimg;
        this.phone = phone;
        this.lastLoginTime = lastLoginTime;
        this.token = token;
        this.menus = menus;
        this.sysRole = sysRole;
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
                ", menus=" + menus +
                ", sysRole=" + sysRole +
                '}';
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
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
