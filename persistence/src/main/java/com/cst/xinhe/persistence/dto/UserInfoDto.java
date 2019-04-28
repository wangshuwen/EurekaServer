package com.cst.xinhe.persistence.dto;


import com.cst.xinhe.persistence.model.menu.SysMenu;
import com.cst.xinhe.persistence.model.role.SysRole;
import com.cst.xinhe.persistence.model.user.SysUser;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName UserInfoDto
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/7 13:47
 * @Vserion v0.0.1
 */

public class UserInfoDto implements Serializable {


    private static final long serialVersionUID = -6471984174085166190L;

    private SysUser sysUser;

    private SysRole sysRole;

    private List<SysMenu> sysMenus;


    public UserInfoDto() {
    }

    public UserInfoDto(SysUser sysUser, SysRole sysRole, List<SysMenu> sysMenus) {
        this.sysUser = sysUser;
        this.sysRole = sysRole;
        this.sysMenus = sysMenus;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "sysUser=" + sysUser +
                ", sysRole=" + sysRole +
                ", sysMenus=" + sysMenus +
                '}';
    }


    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public List<SysMenu> getSysMenus() {
        return sysMenus;
    }

    public void setSysMenus(List<SysMenu> sysMenus) {
        this.sysMenus = sysMenus;
    }


}
