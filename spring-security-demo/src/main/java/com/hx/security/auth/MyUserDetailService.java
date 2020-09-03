package com.hx.security.auth;

import com.hx.security.domain.auth.UserAuthority;
import com.hx.security.domain.entity.User;
import com.hx.security.service.IMenuService;
import com.hx.security.service.IUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MyUserDetailService implements UserDetailsService {
    private final IUserService iUserService;

    private final IMenuService iMenuService;

    public MyUserDetailService(IUserService iUserService, IMenuService iMenuService) {
        this.iUserService = iUserService;
        this.iMenuService = iMenuService;
    }

    @Override
    public UserAuthority loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not exist");
        }

        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUser(user);
        userAuthority.setAuthorities(iMenuService.getMenusByUserId(user.getId()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        return userAuthority;
    }
}
