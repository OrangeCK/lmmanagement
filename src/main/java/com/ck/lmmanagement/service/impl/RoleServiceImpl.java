package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.Role;
import com.ck.lmmanagement.mapper.RoleMapper;
import com.ck.lmmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 01378803
 * @date 2018/11/7 15:16
 * Description  :
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
}
