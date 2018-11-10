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
}
