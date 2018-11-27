package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.Role;
import com.ck.lmmanagement.exception.MyException;
import com.ck.lmmanagement.mapper.EmployeeMapper;
import com.ck.lmmanagement.service.EmployeeService;
import com.ck.lmmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Employee saveForm(Employee form) throws MyException{
        // 保存用户信息
        super.saveForm(form);
        if(form.getId() != null){
            // 循环保存用户角色关系
            for(Role role : form.getRoles()){
                role.setUserId(form.getId());
                roleService.addUserAndRoleRelation(role);
            }
        }else{
            throw new MyException("新增角色的用户ID为为空，请联系管理员");
        }
        return form;
    }

    @Override
    public Employee updateForm(Employee form) throws MyException{
        // 更新用户信息
        super.updateForm(form);
        return form;
    }
}
