package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.Role;
import com.ck.lmmanagement.exception.MyException;
import com.ck.lmmanagement.mapper.EmployeeMapper;
import com.ck.lmmanagement.mapper.RoleMapper;
import com.ck.lmmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private RoleMapper roleMapper;

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
        form = super.saveForm(form);
        form.setId(null);
        if(form.getId() == null){
            throw new MyException("新增角色的用户ID为为空，请联系管理员");
        }else{
            for(Role role : form.getRoles()){
                roleMapper.addUserAndRoleRelation(role);
            }
        }
        return form;
    }
}
