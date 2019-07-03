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
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;
    private Long permissionId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "SysRolePermission{" +
        ", roleId=" + roleId +
        ", permissionId=" + permissionId +
        "}";
    }
}
