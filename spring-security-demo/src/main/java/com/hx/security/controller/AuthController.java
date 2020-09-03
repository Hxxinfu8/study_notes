package com.hx.security.controller;

import com.hx.security.domain.auth.UserAuthority;
import com.hx.security.domain.dto.UserDTO;
import com.hx.security.domain.vo.ResultVO;
import com.hx.security.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResultVO login(@RequestBody UserDTO userDTO) {
        // 用户验证
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

        UserAuthority userAuthority = (UserAuthority) authentication.getPrincipal();
        String token = jwtUtil.generateAccessToken(userAuthority);
        return ResultVO.success(token);
    }
}
