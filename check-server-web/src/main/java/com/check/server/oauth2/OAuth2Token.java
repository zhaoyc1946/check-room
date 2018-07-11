package com.check.server.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description: token
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 21:48:24
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
