package com.hx.webflux.auth;

import com.hx.webflux.domain.auth.UserLogin;
import com.hx.webflux.utils.JwtUtil;
import com.hx.webflux.utils.ServletUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 登出成功事件处理
 *
 * @author hx
 */
@Component
public class MyLogoutSuccessHandler extends RedirectServerLogoutSuccessHandler {
    private final JwtUtil jwtUtil;

    public MyLogoutSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        UserLogin userLogin = jwtUtil.getLoginUser(exchange.getExchange());
        if (userLogin != null) {
            jwtUtil.deleteLoginUser(userLogin);
        }
        return ServletUtil.renderResponse(exchange.getExchange().getResponse(), "登出成功");
    }
}
