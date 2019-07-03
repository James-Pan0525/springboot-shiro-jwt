package com.pwl.shiro.ream;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

/**
 * @author Pan Weilong
 * @date 2019/6/25 17:38
 * @description: 接口.
 */
public class UrlPermissionResolver implements PermissionResolver {
    @Override
    public Permission resolvePermission(String s) {
        return new UrlPermission(s);
    }
}
