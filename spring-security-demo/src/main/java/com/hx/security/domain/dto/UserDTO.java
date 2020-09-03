package com.hx.security.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 用户
 * @author hx
 */
@Data
@Builder
public class UserDTO {
    private String username;
    private String password;
}
