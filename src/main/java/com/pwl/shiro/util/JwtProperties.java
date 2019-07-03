package com.pwl.shiro.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pan Weilong
 * @date 2019/7/2 20:54
 * @description: 接口.
 */
@Configuration
public class JwtProperties {

    /**
     * 秘钥
     **/
    @Value("${security.secret}")
    private String secret;
    /**
     * 过期时间
     **/
    @Value("${token.tokenExpireTime}")
    private Integer tokenExpireTime;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Integer tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }
}
