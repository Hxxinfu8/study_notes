package com.hx.security.auth;

import com.hx.security.domain.constants.HttpCodeEnum;
import com.hx.security.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证失败处理类
 *
 * @author hx
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        log.info(e.getMessage());
        if (e instanceof BadCredentialsException) {
            ServletUtil.renderResponse(httpServletResponse, HttpCodeEnum.USER_OR_PASSWORD_ERROR);
        } else {
            ServletUtil.renderResponse(httpServletResponse, HttpCodeEnum.AUTHENTICATION_FAILED);
        }
    }
}
