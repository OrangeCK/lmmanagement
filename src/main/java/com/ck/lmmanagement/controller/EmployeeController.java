package com.ck.lmmanagement.controller;

import com.alibaba.fastjson.JSON;
import com.ck.lmmanagement.constant.LmEnum;
import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.PageList;
import com.ck.lmmanagement.domain.ResultData;
import com.ck.lmmanagement.exception.MyException;
import com.ck.lmmanagement.service.EmployeeService;
import com.ck.lmmanagement.util.JwtUtil;
import com.ck.lmmanagement.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 01378803
 * @date 2018/11/11 14:53
 * Description  : 用户管理
 */
@RestController
@RequestMapping("/employee")
@CacheConfig(cacheNames = "employee")
@Api(description = "用户管理的相关接口")
public class EmployeeController {
    private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    EmployeeService employeeService;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 用户的分页查询
     * @param employee 用户对象
     * @return 分页工具类
     */
    @Cacheable()
    @RequiresPermissions("employeePageList")
    @ApiOperation(value = "用户的分页查询", notes = "用户的分页查询")
    @ApiImplicitParam(name = "employee",value = "用户信息",required = true,paramType = "body",dataType = "Employee")
    @RequestMapping(value = "/employeePageList", method = RequestMethod.POST)
    public PageList<Employee> employeePageList(@RequestBody Employee employee){
        return employeeService.getPageList(employee);
    }

    /**
     * 新增用户
     * @param employee 用户对象
     * @return 返回结果类
     */
    @RequiresPermissions("addEmployee")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParam(name = "employee",value="用户信息",required = true,paramType = "body",dataType = "Employee")
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ResultData addEmployee(@RequestBody Employee employee, HttpServletRequest request){
        logger.info("正在新增用户");
        try {
            // 得到当前登录用户的信息
            this.getCurrentUser(employee, request);
            employee = employeeService.saveForm(employee);
            if(employee.isEnableFlag()){
                return new ResultData("新增成功");
            }else{
                return new ResultData(LmEnum.RETURN_NUM_201.getNum(),"fail", employee.getReturnMsg());
            }
        } catch (MyException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            logger.error(e.getMsg() + sw.toString());
            return new ResultData(LmEnum.RETURN_NUM_202.getNum(), "fail", e.getMsg());
        }
    }

    /**
     * 更新用户
     * @param employee 用户对象
     * @return 返回结果类
     */
    @RequiresPermissions("updateEmployee")
    @ApiOperation(value = "更新用户", notes = "更新用户")
    @ApiImplicitParam(name = "employee",value="用户信息",required = true,paramType = "body",dataType = "Employee")
    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    public ResultData updateEmployee(@RequestBody Employee employee, HttpServletRequest request){
        logger.info("正在更新用户");
        try {
            // 得到当前登录用户的信息
            this.getCurrentUser(employee, request);
            employee = employeeService.updateForm(employee);
            if(employee.isEnableFlag()){
                return new ResultData("更新成功");
            }else{
                return new ResultData(LmEnum.RETURN_NUM_201.getNum(),"fail", employee.getReturnMsg());
            }
        } catch (MyException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            logger.error(e.getMsg() + sw.toString());
            return new ResultData(LmEnum.RETURN_NUM_202.getNum(), "fail", e.getMsg());
        }
    }

    /**
     * 失效用户
     * @param employee 用户信息
     * @return
     */
    @RequiresPermissions("disableEmployee")
    @ApiOperation(value = "失效用户", notes = "失效用户")
    @ApiImplicitParam(name = "id",value="用户id",required = true,paramType = "query", dataType = "Long")
    @RequestMapping(value = "/disableEmployee", method = RequestMethod.POST)
    public ResultData disableEmployee(@RequestBody Employee employee, HttpServletRequest request){
        // 得到当前登录用户的信息
        this.getCurrentUser(employee, request);
        logger.info("正在删除用户");
        int count = employeeService.updateToDisable(employee);
        if(count > 0){
            return new ResultData();
        }else{
            return new ResultData("fail", "操作失败");
        }
    }

    /**
     * 从redis获取当前登录人的信息
     * @param request
     * @return
     */
    private void getCurrentUser(Employee employee, HttpServletRequest request){
        String token = request.getHeader(LmEnum.AUTHORIZATION.getName());
        Employee emp = JSON.parseObject(redisUtil.hget(token, LmEnum.USER_INFO.getName()).toString(), Employee.class);
        employee.setCreationBy(emp.getId());
        employee.setUpdatedBy(emp.getId());
        employee.setUserName(emp.getUserName());
    }
}
