package com.ck.lmmanagement.service;


import com.ck.lmmanagement.domain.Employee;

/**
 * @author 01378803
 * @date 2018/11/7 15:13
 * Description  :
 */
public interface EmployeeService extends BaseService<Employee> {
    /**
     * 检查账号
     * @param loginName
     * @param password
     * @return
     */
    Employee loginAccount(String loginName, String password);
    /**
     * 检查账号
     * @param loginName
     * @return
     */
    Employee loginAccountByLoginName(String loginName);
}
