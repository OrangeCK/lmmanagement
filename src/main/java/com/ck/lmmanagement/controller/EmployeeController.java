package com.ck.lmmanagement.controller;

import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.PageList;
import com.ck.lmmanagement.domain.ResultData;
import com.ck.lmmanagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 01378803
 * @date 2018/11/11 14:53
 * Description  : 用户管理
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    /**
     * 用户的分页查询
     * @param employee 用户对象
     * @return 分页工具类
     */
    @RequestMapping(value = "/employeePageList", method = RequestMethod.GET)
    public PageList<Employee> employeePageList(Employee employee){
        logger.info("用户的分页查询");
        return employeeService.getPageList(employee);
    }

    /**
     * 新增用户
     * @param employee 用户对象
     * @return 返回结果类
     */
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ResultData addEmployee(@RequestBody Employee employee){
        logger.info("正在新增用户");
        employee = employeeService.saveForm(employee);
        if(employee.isEnableFlag()){
            return new ResultData("新增成功");
        }else{
            return new ResultData("fail", "新增失败");
        }
    }
}
