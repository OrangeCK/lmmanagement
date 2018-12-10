package com.ck.lmmanagement.controller;

import com.ck.lmmanagement.constant.LmEnum;
import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.PageList;
import com.ck.lmmanagement.domain.ResultData;
import com.ck.lmmanagement.exception.MyException;
import com.ck.lmmanagement.service.EmployeeService;
import com.ck.lmmanagement.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private EmployeeService employeeService;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 用户的分页查询
     * @param employee 用户对象
     * @return 分页工具类
     */
    @Cacheable()
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
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParam(name = "employee",value="用户信息",required = true,paramType = "body",dataType = "Employee")
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ResultData addEmployee(@RequestBody Employee employee){
        logger.info("正在新增用户");
        try {
            employee = employeeService.saveForm(employee);
            if(employee.isEnableFlag()){
                return new ResultData("新增成功");
            }else{
                return new ResultData(LmEnum.RETURN_NUM_204.getNum(),"fail", "新增失败");
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
    @ApiOperation(value = "更新用户", notes = "更新用户")
    @ApiImplicitParam(name = "employee",value="用户信息",required = true,paramType = "body",dataType = "Employee")
    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    public ResultData updateEmployee(@RequestBody Employee employee){
        logger.info("正在更新用户");
        try {
            employee = employeeService.updateForm(employee);
            if(employee.isEnableFlag()){
                return new ResultData("更新成功");
            }else{
                return new ResultData(LmEnum.RETURN_NUM_204.getNum(),"fail", "更新失败");
            }
        } catch (MyException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            logger.error(e.getMsg() + sw.toString());
            return new ResultData(LmEnum.RETURN_NUM_202.getNum(), "fail", e.getMsg());
        }
    }
}
