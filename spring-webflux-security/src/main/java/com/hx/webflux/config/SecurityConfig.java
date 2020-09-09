package com.hx.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

/**
 * 安全认证配置
 *
 * @author hx
 */

/**
 * 启用webflux登陆权限校验
 */
@EnableWebFluxSecurity
/**
 * 启用@PreAuthorize注解配置，如果不加这个注解的话，即使方法中加了@PreAuthorize也不会生效
 */
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private final ReactiveUserDetailsService userDetailsService;
    private final ServerAccessDeniedHandler accessDeniedHandler;
    private final RedirectServerLogoutSuccessHandler logoutSuccessHandler;
    private final ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;
    private final ServerSecurityContextRepository securityContextRepository;

    public SecurityConfig(ReactiveUserDetailsService userDetailsService, ServerAccessDeniedHandler accessDeniedHandler, RedirectServerLogoutSuccessHandler logoutSuccessHandler, ServerAuthenticationEntryPoint serverAuthenticationEntryPoint, ServerSecurityContextRepository securityContextRepository) {
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.serverAuthenticationEntryPoint = serverAuthenticationEntryPoint;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                // 认证异常处理
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(serverAuthenticationEntryPoint)
                .and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .httpBasic().disable()
                .securityContextRepository(securityContextRepository)
                .authenticationManager(authenticationManager())
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/auth/login").permitAll()
                .anyExchange().authenticated()
                // 默认接受POST登出
                .and().logout().logoutUrl("/auth/logout").logoutSuccessHandler(logoutSuccessHandler)
                .and().build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    /**
     * 装载BCrypt密码编码器
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
