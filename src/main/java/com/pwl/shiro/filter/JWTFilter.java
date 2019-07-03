package com.pwl.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwl.shiro.common.ResultVO;
import com.pwl.shiro.exception.ShiroException;
import com.pwl.shiro.ream.JWTToken;
import com.pwl.shiro.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author Pan Weilong
 * @date 2019/6/24 22:24
 * @description: 接口.
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String secret;

    private Integer tokenExpireTime;

    private StringRedisTemplate stringRedisTemplate;

    public JWTFilter(String secret, Integer tokenExpireTime, StringRedisTemplate stringRedisTemplate) {
        this.secret = secret;
        this.tokenExpireTime = tokenExpireTime;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    /**
     * 执行登录流程
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");

        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * @Author Pan Weilong
     * @Description 是否允许访问 true允许访问。当方法返回false时才会执行isAccessAllowed方法
     * @Date 15:23 2019/6/27
     * @Param [request, response, mappedValue]
     * @return boolean
     **/
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        if (isLoginAttempt(request, response)) {
            //登录验证
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                String token = httpServletRequest.getHeader("Authorization");
                //处理token过期的问题，登入成功后将token存入redis,并将过期时间设置为token过期的2倍，
                //当token过期后,判断redis中的token是否存在，存在就生成新的token给前端,否则token过期,重新登录
                //这里没有处理并发问题
                if(stringRedisTemplate.hasKey(token)){
                    //重新生成token
                    String newToken = JWTUtil.sign(JWTUtil.getUserId(token), secret);
                    stringRedisTemplate.opsForValue().set(newToken,newToken);
                    stringRedisTemplate.expire(newToken,2*tokenExpireTime, TimeUnit.MINUTES);
                    //删掉以前的token
                    stringRedisTemplate.delete(token);
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setHeader("Authorization", newToken);
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                    return true;
                }
                throw new AuthenticationException("token有误");
            }
        }else{
            throw new ShiroException("token不能为空");
        }
        //url鉴权
        Subject subject = getSubject(request,response);
        //获取请求方式
        String method = WebUtils.toHttp(request).getMethod();
        //subject.isPermitted返回false表示未授权 true已授权 会去调用ream中的授权 这里控制的方法级别 /GET/sysUser/**
        //如果访问的url没有被授权则会拒绝访问，走访问拒绝的处理逻辑isAccessAllowed，有则放行
        return subject.isPermitted("/"+method+getPathWithinApplication(request));
    }

    /**
     * onAccessDenied：当 isAccessAllowed 返回 false 的时候，才会执行 method onAccessDenied

     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        logger.debug("当 isAccessAllowed 返回 false 的时候，才会执行 method onAccessDenied ");
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;
        logger.debug("授权失败，禁止访问:{}",getPathWithinApplication(request));
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ResultVO<String> result = new ResultVO<>();
        result.setCode(403);
        result.setMsg("授权失败，禁止访问");
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
        return false;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
