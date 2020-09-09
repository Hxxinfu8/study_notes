package com.hx.webflux.service;

import com.hx.webflux.domain.entity.PmUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PmUserService {
    /**
     * 根据登录账户查询
     *
     * @param account 账户
     * @return user
     */
    Mono<PmUser> findByAccount(String account);

    /**
     * 根据用户id查询菜单权限
     *
     * @param userId 用户id
     * @return 菜单权限
     */
    Flux<String> getMenusByUserId(Long userId);
}
