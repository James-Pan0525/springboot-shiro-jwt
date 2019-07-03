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
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;
    private String roleName;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "SysRole{" +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        "}";
    }
}
