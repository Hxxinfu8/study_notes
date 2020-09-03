package com.hx.security.auth;

import com.alibaba.fastjson.JSONObject;
import com.hx.security.domain.auth.UserAuthority;
import com.hx.security.domain.vo.ResultVO;
import com.hx.security.utils.JwtUtil;
import com.hx.security.utils.ServletUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功处理
 *
 * @author hx
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    private final JwtUtil jwtUtil;

    public MyLogoutSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserAuthority userAuthority = jwtUtil.getLoginUser(httpServletRequest);

        if (userAuthority != null) {
            jwtUtil.deleteLoginUser(userAuthority);
        }
        String body = JSONObject.toJSONString(ResultVO.success("登出成功"));
        ServletUtil.renderResponse(httpServletResponse, body);
    }
}
