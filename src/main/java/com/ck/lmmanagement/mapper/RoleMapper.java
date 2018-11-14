package com.ck.lmmanagement.mapper;

import com.ck.lmmanagement.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 01378803
 * @date 2018/11/7 15:20
 * Description  :
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 增加用户与角色之前的关系
     * @param role 角色对象
     * @return
     */
    Integer addUserAndRoleRelation(Role role);
}
