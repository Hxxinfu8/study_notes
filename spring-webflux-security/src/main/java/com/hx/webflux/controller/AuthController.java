package com.hx.webflux.controller;

import com.hx.webflux.domain.auth.UserLogin;
import com.hx.webflux.domain.dto.UserLoginDTO;
import com.hx.webflux.domain.vo.ResultVO;
import com.hx.webflux.utils.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 认证controller
 *
 * @author hx
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public Mono<ResultVO> login(@RequestBody UserLoginDTO loginDTO) {
        Mono<Authentication> authenticationMono = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        return authenticationMono
                .map(authentication -> {
                    UserLogin userLogin = (UserLogin) authentication.getPrincipal();
                    String token = jwtUtil.generateAccessToken(userLogin);
                    return ResultVO.success(token);
                });
    }

    @PreAuthorize("hasAuthority('/userManage')")
    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("hello");
    }
}
