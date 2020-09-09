package com.hx.webflux.auth;

import com.hx.webflux.domain.auth.UserLogin;
import com.hx.webflux.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 上下文处理
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private final JwtUtil jwtUtil;

    public SecurityContextRepository(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        UserLogin userLogin = jwtUtil.getLoginUser(exchange);
        if (userLogin != null) {
            jwtUtil.verifyToken(userLogin);
            Authentication auth = new UsernamePasswordAuthenticationToken(userLogin, null, userLogin.getAuthorities());
            return Mono.just(new SecurityContextImpl(auth));
        }
        return Mono.empty();
    }
}
