package com.cst.xinhe.persistence.model.sys_menu_role;


import com.cst.xinhe.base.entity.BaseEntity;
import com.cst.xinhe.persistence.model.menu.SysMenu;
import com.cst.xinhe.persistence.model.role.SysRole;

public class SysMenuRole extends BaseEntity {

    private SysRole sysRole;

    private SysMenu sysMenu;

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysMenu getSysMenu() {
        return sysMenu;
    }

    public void setSysMenu(SysMenu sysMenu) {
        this.sysMenu = sysMenu;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu_role.menu_role_id
     *
     * @mbg.generated
     */
    private Integer menuRoleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu_role.sys_role_id
     *
     * @mbg.generated
     */
    private Integer sysRoleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_menu_role.sys_menu_id
     *
     * @mbg.generated
     */
    private Integer sysMenuId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu_role.menu_role_id
     *
     * @return the value of sys_menu_role.menu_role_id
     *
     * @mbg.generated
     */
    public Integer getMenuRoleId() {
        return menuRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu_role.menu_role_id
     *
     * @param menuRoleId the value for sys_menu_role.menu_role_id
     *
     * @mbg.generated
     */
    public void setMenuRoleId(Integer menuRoleId) {
        this.menuRoleId = menuRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu_role.sys_role_id
     *
     * @return the value of sys_menu_role.sys_role_id
     *
     * @mbg.generated
     */
    public Integer getSysRoleId() {
        return sysRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu_role.sys_role_id
     *
     * @param sysRoleId the value for sys_menu_role.sys_role_id
     *
     * @mbg.generated
     */
    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_menu_role.sys_menu_id
     *
     * @return the value of sys_menu_role.sys_menu_id
     *
     * @mbg.generated
     */
    public Integer getSysMenuId() {
        return sysMenuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_menu_role.sys_menu_id
     *
     * @param sysMenuId the value for sys_menu_role.sys_menu_id
     *
     * @mbg.generated
     */
    public void setSysMenuId(Integer sysMenuId) {
        this.sysMenuId = sysMenuId;
    }
}
