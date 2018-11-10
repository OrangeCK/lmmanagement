package com.ck.lmmanagement.mapper;

import com.ck.lmmanagement.domain.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 01378803
 * @date 2018/11/7 15:20
 * Description  :
 */
@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 检查账号
     * @param loginName
     * @param password
     * @return
     */
    Employee loginAccount(@Param("loginName") String loginName, @Param("password") String password);
    /**
     * 检查账号
     * @param loginName
     * @return
     */
    Employee loginAccountByLoginName(@Param("loginName") String loginName);
}
