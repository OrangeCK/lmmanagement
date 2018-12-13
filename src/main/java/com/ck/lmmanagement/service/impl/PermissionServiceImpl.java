package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.Permission;
import com.ck.lmmanagement.mapper.PermissionMapper;
import com.ck.lmmanagement.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author 01378803
 * @date 2018/11/7 15:15
 * Description  :
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<String> findAllPermissionsById(Long id) {
        return permissionMapper.findAllPermissionsById(id);
    }
}
