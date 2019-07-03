package com.pwl.shiro.ream;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Pan Weilong
 * @date 2019/6/24 22:25
 * @description: 接口.
 */
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
