package com.nnk.springboot.domain;

import java.util.Collection;
import java.util.Map;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
 
public class CustomOAuth2User implements OAuth2User {
 
    private OAuth2User oauth2User;
    @lombok.Generated
    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }
    
    @lombok.Generated
    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }
    @lombok.Generated
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }
    @lombok.Generated
    @Override
    public String getName() {
        return oauth2User.getName();
    }
    
}
 