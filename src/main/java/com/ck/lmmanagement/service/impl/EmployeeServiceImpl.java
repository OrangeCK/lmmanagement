package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.mapper.EmployeeMapper;
import com.ck.lmmanagement.service.EmployeeService;
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

    @Override
    public Employee loginAccount(String loginName, String password) {
        return employeeMapper.loginAccount(loginName, password);
    }

    @Override
    public Employee loginAccountByLoginName(String loginName) {
        return employeeMapper.loginAccountByLoginName(loginName);
    }
}
