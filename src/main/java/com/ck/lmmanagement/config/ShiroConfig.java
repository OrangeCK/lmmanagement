package com.ck.lmmanagement.config;

import com.ck.lmmanagement.domain.Permission;
import com.ck.lmmanagement.service.PermissionService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 01378803
 * @date 2018/11/7 18:08
 * Description  : shiro的配置文件
 */
@Configuration
public class ShiroConfig {
    private final static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    @Autowired
    private PermissionService permissionService;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        logger.info("------------------------shiro filter------------------------");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/login/**", "anon");
        // 查询所有权限
        List<Permission> permissionList =  permissionService.findData(null);
        for(Permission permission : permissionList){
            if(StringUtils.isEmpty(permission.getPermissionCode()) || StringUtils.isEmpty(permission.getResources())){
                continue;
            }
            filterChainDefinitionMap.put(permission.getResources(), "perms[" + permission.getPermissionCode() + "]");
        }
        logger.info("所有权限:{}", filterChainDefinitionMap);
        filterChainDefinitionMap.put("/**", "authc");
        // 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/user/unlogin");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    /**
     * shiro 用户数据注入
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }
    /**
     * 配置管理层。即安全控制层
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /**
     * 开启shiro aop注解支持 使用代理方式所以需要开启代码支持
     *  一定要写入上面advisorAutoProxyCreator（）自动代理。不然AOP注解不会生效
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
