package com.ck.lmmanagement.config;

import com.ck.lmmanagement.domain.Employee;
import com.ck.lmmanagement.domain.JwtToken;
import com.ck.lmmanagement.domain.Permission;
import com.ck.lmmanagement.domain.Role;
import com.ck.lmmanagement.service.EmployeeService;
import com.ck.lmmanagement.service.PermissionService;
import com.ck.lmmanagement.service.RoleService;
import com.ck.lmmanagement.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author 01378803
 * @date 2018/12/12 14:03
 * Description  :
 */
public class JwtShiroRealm extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(JwtShiroRealm.class);
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    /**
     * jwt整合shiro必须重写此方法，不然Shiro会报错，限定这个Realm只支持我们自定义的JWT Token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 权限验证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("权限验证");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String loginName = JwtUtil.getLoginName(principalCollection.toString());
        if(loginName != null){
            // 与数据库数据进行匹配校验
            Employee employee = employeeService.loginAccountByLoginName(loginName);
            try {
                // 注入角色
                Set<String> roleSet = roleService.findAllRolesById(employee.getId());
                authorizationInfo.setRoles(roleSet);
                // 注入权限
                Set<String> permissionSet = permissionService.findAllPermissionsById(employee.getId());
                authorizationInfo.setStringPermissions(permissionSet);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("权限配置出错！");
            }
            logger.info("用户" + employee.getUserName() + "的权限:{}", authorizationInfo.getStringPermissions());
            logger.info("用户" + employee.getUserName() + "的角色:{}", authorizationInfo.getRoles());
            return authorizationInfo;
        }
        return null;
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
        String token = (String) authenticationToken.getCredentials();
        // 获取用户名和密码
        String loginName = JwtUtil.getLoginName(token);
        if(loginName == null){
            throw new AccountException("token身份认证失败，token格式不正确");
        }
        // 与数据库数据进行匹配校验
        Employee employee = employeeService.loginAccountByLoginName(loginName);
        if(null == employee){
            throw new UnknownAccountException("该用户不存在");
        }
        if(!JwtUtil.verify(token, loginName, employee.getPassword())){
            throw new IncorrectCredentialsException("token身份认证失败，token失效");
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
