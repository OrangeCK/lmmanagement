package com.ck.lmmanagement.config;

import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.Permission;
import com.ck.lmmanagement.domain.Role;
import com.ck.lmmanagement.service.EmployeeService;
import com.ck.lmmanagement.service.PermissionService;
import com.ck.lmmanagement.service.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 01378803
 * @date 2018/11/7 14:50
 * Description  : shiro的realm
 */
public class ShiroRealm extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    /**
     * 权限验证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("权限配置");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Employee employee = (Employee)principalCollection.getPrimaryPrincipal();
        try {
            // 注入角色
            List<Role> roleList = roleService.findDataById(employee.getId());
            for(Role role : roleList){
                authorizationInfo.addRole(role.getRoleCode());
            }
            // 注入权限
            List<Permission> permissionList = permissionService.findDataById(employee.getId());
            for(Permission permission : permissionList){
                authorizationInfo.addStringPermission(permission.getPermissionCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("权限配置出错！");
        }
        logger.info("用户" + employee.getUserName() + "的权限:{}", authorizationInfo.getStringPermissions());
        logger.info("用户" + employee.getUserName() + "的角色:{}", authorizationInfo.getRoles());
        return authorizationInfo;
    }
    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("用户验证");
        // 从token中储存这输入的用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken)authenticationToken;
        // 获取用户名和密码
        String loginName = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        // 与数据库数据进行匹配校验
        Employee employee = employeeService.loginAccountByLoginName(loginName);
        if(null == employee){
            throw new UnknownAccountException();
        }else{
            if(password.equals(employee.getPassword())){
                SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(employee, password.toCharArray(), getName());
                return authorizationInfo;
            }else{
                throw new IncorrectCredentialsException();
            }
        }
    }
}
