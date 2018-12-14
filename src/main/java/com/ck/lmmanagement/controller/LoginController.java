package com.ck.lmmanagement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ck.lmmanagement.constant.LmEnum;
import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.ResultData;
import com.ck.lmmanagement.service.EmployeeService;
import com.ck.lmmanagement.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录
     * @param jsonStr 用户信息的json字符串
     * @return
     */
    @RequestMapping(value = "/goLogin", method = RequestMethod.POST)
    public ResultData login(@RequestBody String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        // 登录名
        String loginName = jsonObject.getString("loginName");
        // 登录密码
        String password = jsonObject.getString("password");
        String realPassword = employeeService.getPasswordByLoginName(loginName);
        if(realPassword == null){
            return new ResultData(LmEnum.RETURN_NUM_401.getNum(), "fail", "用户名错误");
        }else if(!realPassword.equals(password)){
            return new ResultData(LmEnum.RETURN_NUM_401.getNum(), "fail", "密码错误");
        }else{
            return new ResultData(JwtUtil.sign(loginName, password));
        }
    }

    @RequestMapping(value = "/unAuthorization", method = RequestMethod.POST)
    public ResultData unAuthorization(){
        return new ResultData(LmEnum.RETURN_NUM_401.getNum(), "fail", "登录身份验证错误");
    }
}
