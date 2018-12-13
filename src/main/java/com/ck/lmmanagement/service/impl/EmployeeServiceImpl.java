package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.Role;
import com.ck.lmmanagement.exception.MyException;
import com.ck.lmmanagement.mapper.EmployeeMapper;
import com.ck.lmmanagement.service.EmployeeService;
import com.ck.lmmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

/**
 * @author 01378803
 * @date 2018/11/7 15:15
 * Description  :
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RoleService roleService;

    @Override
    public Employee loginAccount(String loginName, String password) {
        return employeeMapper.loginAccount(loginName, password);
    }

    @Override
    public Employee loginAccountByLoginName(String loginName) {
        return employeeMapper.loginAccountByLoginName(loginName);
    }

    @Override
    public String getPasswordByLoginName(String loginName) {
        return employeeMapper.getPasswordByLoginName(loginName);
    }

    @Override
    public Employee saveForm(Employee employee) throws MyException{
        // 保存之前的校验
        employee = checkForm(employee);
        // enableFlag等于true表示通过校验
        if(employee.isEnableFlag()){
            // 保存用户信息
            super.saveForm(employee);
            if(employee.getId() != null){
                // 循环保存用户角色关系
                for(Role role : employee.getRoles()){
                    role.setUserId(employee.getId());
                    roleService.addUserAndRoleRelation(role);
                }
            }else{
                throw new MyException("新增用户失败，用户ID为为空");
            }
        }
        return employee;
    }

    @Override
    public Employee updateForm(Employee employee) throws MyException{
        // 更新之前的校验
        employee = checkForm(employee);
        // enableFlag等于true表示通过校验
        if(employee.isEnableFlag()){
            // 更新用户信息
            super.updateForm(employee);
            // 删除用户之前的角色信息
            roleService.delUserAndRoleRelation(employee.getId());
            if(employee.getId() != null){
                // 循环保存用户角色关系
                for(Role role : employee.getRoles()){
                    role.setUserId(employee.getId());
                    roleService.addUserAndRoleRelation(role);
                }
            }else{
                throw new MyException("更新用户失败，用户ID为为空");
            }
        }
        return employee;
    }

    /**
     * 保存更新之前的校验
     * @param employee 用户信息
     */
    private Employee checkForm(Employee employee){
        if(employee.isEnableFlag()){
            if(StringUtils.isEmpty(employee.getLoginName()) || StringUtils.isEmpty(employee.getUserName())
                    || StringUtils.isEmpty(employee.getSex()) || StringUtils.isEmpty(employee.getUserPhone())){
                employee.setEnableFlag(false);
                employee.setReturnMsg("登录名、用户名称、性别、电话等不能为空");
                return employee;
            }
        }
        return employee;
    }
}
