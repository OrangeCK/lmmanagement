package com.ck.lmmanagement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.ResultData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 01378803
 * @date 2018/11/8 9:37
 * Description  :
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录
     * @param jsonStr 用户信息的json字符串
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "/goLogin", method = RequestMethod.POST)
    public ResultData login(@RequestBody String jsonStr, HttpSession session, HttpServletResponse response){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        // 登录名
        String loginName = jsonObject.getString("loginName");
        // 登录密码
        String password = jsonObject.getString("password");
        // 创建用户名和密码的令牌
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        // subject理解为用户对象
        Subject subject = SecurityUtils.getSubject();
        ResultData resultData = null;
        try {
            subject.login(token);
            resultData = new ResultData("登陆成功");
            logger.info("账号为" + loginName + "登陆成功");
        } catch (UnknownAccountException e) {
            resultData = new ResultData("fail","登录账号不存在");
            logger.error("账号为" + loginName + "登录账号不存在");
            e.printStackTrace();
        } catch (IncorrectCredentialsException e) {
            resultData = new ResultData("fail","密码验证错误");
            logger.error("账号为" + loginName + "密码验证错误");
            e.printStackTrace();
        } catch (AuthenticationException e) {
            resultData = new ResultData("fail","登录失败");
            logger.error("账号为" + loginName + "登录失败");
            e.printStackTrace();
        }
        return resultData;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ResultData loginInfo(){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        if(null == employee){
            return new ResultData("fail", "无登录用户");
        }else{
            ResultData resultData = new ResultData();
            resultData.setData(employee);
            return resultData;
        }
    }
}
