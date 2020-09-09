package com.hx.webflux.domain.dto;

import lombok.Data;

/**
 * 用户登录
 *
 * @author hx
 */
@Data
public class UserLoginDTO {
    private String username;
    private String password;
}
