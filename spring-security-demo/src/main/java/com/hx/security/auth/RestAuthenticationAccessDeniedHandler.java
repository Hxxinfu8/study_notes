package com.hx.security.auth;

import com.hx.security.domain.constants.HttpCodeEnum;
import com.hx.security.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限不足处理类
 *
 * @author hx
 */
@Component
@Slf4j
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        log.info("权限不足：" + e.getMessage());
        ServletUtil.renderResponse(httpServletResponse, HttpCodeEnum.NO_AUTHORITY);
    }
}
