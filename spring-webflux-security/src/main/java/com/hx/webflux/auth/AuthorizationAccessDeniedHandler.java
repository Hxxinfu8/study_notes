package com.hx.webflux.auth;

import com.hx.webflux.domain.constants.HttpCodeEnum;
import com.hx.webflux.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 权限失败处理
 */
@Component
@Slf4j
public class AuthorizationAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        log.info(denied.getMessage());
        return ServletUtil.renderResponse(exchange.getResponse(), HttpCodeEnum.NO_AUTHORITY);
    }
}
