package com.hx.security.domain.auth;

import com.hx.security.domain.entity.User;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * 登录用户信息
 *
 * @author hx
 */
@Data
public class UserAuthority implements UserDetails {
    /**
     * 登陆时间
     */
    private Long loginTime;

    private Set<SimpleGrantedAuthority> authorities;

    /**
     * 过期时间
     */
    private Long expireTime;

    private User user;

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
