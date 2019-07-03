package com.pwl.shiro.ream;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.pwl.shiro.entity.SysUser;
import com.pwl.shiro.service.SysPermissionService;
import com.pwl.shiro.service.SysUserService;
import com.pwl.shiro.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pan Weilong
 * @date 2019/6/20 20:11
 * @description: 认证和鉴权接口
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

    //token密钥
    @Value("${security.secret}")
    private String secret;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //String userId=principals.getPrimaryPrincipal().toString();
        //从数据库读取资源
        //可以控制到GET POST请求
        List<String> sysPermissions=new ArrayList<>();
        sysPermissions.add("/GET/sysUser/**");
        sysPermissions.add("/DELETE/sysUser");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(sysPermissions);
        return info;
    }

    /**
     * 认证流程
     *
     * @param
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得userId，用于和数据库进行对比
        String userId = JWTUtil.getUserId(token);
        if (userId == null) {
            throw new AuthenticationException("token invalid");
        }
        //查询用户是否存在
        Wrapper<SysUser> objectEntityWrapper = new EntityWrapper<>();
        objectEntityWrapper.eq("user_id","1");
        SysUser sysUser = sysUserService.selectOne(objectEntityWrapper);
        if (sysUser == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        //校验token的正确性
        if (! JWTUtil.verify(token, userId, secret)) {
            throw new AuthenticationException("Token expired or incorrect.");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

}
