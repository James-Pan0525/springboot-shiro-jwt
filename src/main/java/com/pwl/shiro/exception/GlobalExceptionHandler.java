package com.pwl.shiro.exception;

import com.pwl.shiro.common.ResultVO;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Pan Weilong
 * @Description 全局异常捕获
 * @Date 15:11 2019/6/20
 * @Param
 * @return
 **/
@ControllerAdvice
public class GlobalExceptionHandler implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ApplicationContext applicationContext;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        if(e instanceof UnauthenticatedException){
            return new ResultVO().returnFail("无认证");
        }
        return new ResultVO().returnFail(e.getMessage());
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
