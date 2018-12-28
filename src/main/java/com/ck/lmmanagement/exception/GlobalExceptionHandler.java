package com.ck.lmmanagement.exception;

import com.ck.lmmanagement.domain.ResultData;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 01378803
 * @date 2018/12/14 12:13
 * Description  :
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 不满足@RequiresGuest注解时抛出的异常信息
     */
    private static final String GUEST_ONLY = "Attempting to perform a guest-only operation";

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public ResultData handleShiroException(ShiroException e, HttpServletResponse response) {
        String eName = e.getClass().getSimpleName();
        response.setStatus(401);
        return new ResultData(401, "fail","鉴权或授权过程出错");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public ResultData page401(UnauthenticatedException e, HttpServletResponse response) {
        String eMsg = e.getMessage();
        if (StringUtils.startsWithIgnoreCase(eMsg,GUEST_ONLY)){
            response.setStatus(403);
            return new ResultData(403, "fail","只允许游客访问，若您已登录，请先退出登录");
        }else{
            response.setStatus(401);
            return new ResultData(401, "fail","用户未登录");
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResultData page403(HttpServletResponse response) {
        response.setStatus(403);
        return new ResultData(403, "fail", "用户没有访问权限");
    }

}
