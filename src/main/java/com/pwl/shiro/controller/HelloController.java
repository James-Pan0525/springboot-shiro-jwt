package com.pwl.shiro.controller;

import com.pwl.shiro.common.ResultVO;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pan Weilong
 * @date 2019/6/20 15:06
 * @description: 接口.
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResultVO hello(){
        return new ResultVO("1");
    }

    public static void main(String[] args) {
        SimpleHash simpleHash = new SimpleHash("md5", "zhangsan".getBytes(), "1", 1);
        System.out.println(simpleHash.toHex());
    }
}
