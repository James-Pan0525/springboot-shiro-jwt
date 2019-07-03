package com.pwl.shiro.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.pwl.shiro.common.ResultVO;
import com.pwl.shiro.entity.SysUser;
import com.pwl.shiro.service.SysUserService;
import com.pwl.shiro.util.JWTUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Pan Weilong
 * @date 2019/6/20 21:00
 * @description: 接口.
 */
@RestController
public class LonginController {

    @Value("${security.secret}")
    private String secret;
    @Value("${token.tokenExpireTime}")
    private Integer tokenExpireTime;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public ResultVO login(@RequestBody SysUser sysUserReq) {
        Wrapper<SysUser> sysUserEntityWrapper = new EntityWrapper<>();
        sysUserEntityWrapper.eq("user_name",sysUserReq.getUserName());
        SysUser sysUserDB = sysUserService.selectOne(sysUserEntityWrapper);
        //加密密码
        SimpleHash simpleHash = new SimpleHash("md5", sysUserReq.getPassword().getBytes(), sysUserDB.getSalt(), 1);
        if(sysUserDB.getPassword().equals(simpleHash.toHex())){
            String token = JWTUtil.sign(sysUserDB.getUserId().toString(), secret);
            stringRedisTemplate.opsForValue().set(token,token);
            stringRedisTemplate.expire(token,2*tokenExpireTime, TimeUnit.MINUTES);
            return new ResultVO().returnSuccess(token);
        }else{
            throw new UnauthorizedException("用户名或密码错误");
        }
    }
}
