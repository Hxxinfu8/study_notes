package com.hx.webflux.auth;

import com.hx.webflux.domain.constants.HttpCodeEnum;
import com.hx.webflux.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证失败处理类
 *
 * @author hx
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        log.info(e.getMessage());
        if (e instanceof BadCredentialsException) {
            return ServletUtil.renderResponse(exchange.getResponse(), HttpCodeEnum.USER_OR_PASSWORD_ERROR);
        } else if (e instanceof UsernameNotFoundException) {
            return ServletUtil.renderResponse(exchange.getResponse(), HttpCodeEnum.USER_NOT_EXITS);
        } else if (e instanceof AuthenticationCredentialsNotFoundException) {
            return ServletUtil.renderResponse(exchange.getResponse(), HttpCodeEnum.AUTHENTICATION_FAILED);
        } else {
            return ServletUtil.renderResponse(exchange.getResponse(), HttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
