package com.hx.webflux.service.impl;

import com.hx.webflux.domain.entity.PmUser;
import com.hx.webflux.reposiroty.PmUserRepository;
import com.hx.webflux.service.PmUserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 用户service实现
 *
 * @author hx
 */
@Service
public class PmUserServiceImpl implements PmUserService {
    @Resource
    private PmUserRepository pmUserRepository;

    @Override
    public Mono<PmUser> findByAccount(String account) {
        return pmUserRepository.findByAccount(account);
    }

    @Override
    public Flux<String> getMenusByUserId(Long userId) {
        return pmUserRepository.getMenusByUserId(userId);
    }
}
