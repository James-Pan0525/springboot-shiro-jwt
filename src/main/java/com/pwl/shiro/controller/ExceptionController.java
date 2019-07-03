package com.pwl.shiro.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pan Weilong
 * @date 2019/6/25 21:55
 * @description: 处理filter抛出的异常，相当于springboot全局异常
 */
@RestControllerAdvice
public class ExceptionController extends BasicErrorController {

    public ExceptionController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }
    public ExceptionController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    public ExceptionController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    /**
     * @Author Pan Weilong
     * @Description 处理传过来的异常(解决自定义全局异常捕获不到的问题)
     * @Date 17:16 2019/6/27
     * @Param [request]
     * @return org.springframework.http.ResponseEntity<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        String code=body.get("status").toString();
        String message = body.get("message").toString();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",code);
        map.put("msg",message);
        return new ResponseEntity<Map<String, Object>>(map, status);
    }
}
