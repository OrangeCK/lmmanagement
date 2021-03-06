package com.ck.lmmanagement.domain;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 01378803
 * @date 2018/12/12 13:40
 * Description  : JWT的token对象
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
