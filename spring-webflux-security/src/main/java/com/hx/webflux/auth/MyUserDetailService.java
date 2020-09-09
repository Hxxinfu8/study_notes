package com.hx.webflux.auth;

import com.hx.webflux.domain.auth.UserLogin;
import com.hx.webflux.domain.constants.HttpCodeEnum;
import com.hx.webflux.domain.entity.PmUser;
import com.hx.webflux.service.PmUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;

/**
 * 自定义用户信息
 *
 * @author hx
 */
@Component
public class MyUserDetailService implements ReactiveUserDetailsService {
    private final PmUserService pmUserService;

    public MyUserDetailService(PmUserService pmUserService) {
        this.pmUserService = pmUserService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<PmUser> pmUserMono = pmUserService.findByAccount(username);
        return pmUserMono
                .switchIfEmpty(Mono.error(() -> new UsernameNotFoundException(HttpCodeEnum.USER_NOT_EXITS.message())))
                .flatMap(pmUser -> pmUserService.getMenusByUserId(pmUser.getId())
                        .collect(HashSet<SimpleGrantedAuthority>::new, (authorityList, permission) -> authorityList.add(new SimpleGrantedAuthority(permission)))
                        .zipWith(pmUserMono, (authorityList, user) -> {
                            UserLogin userLogin = new UserLogin();
                            userLogin.setUser(user);
                            userLogin.setAuthorities(authorityList);
                            return userLogin;
                        }));
    }
}
