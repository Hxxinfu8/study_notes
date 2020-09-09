package com.hx.webflux.domain.constants;

/**
 * 返回状态枚举
 *
 * @author hx
 */
public enum HttpCodeEnum {
    AUTHENTICATION_FAILED(401, "认证失败"),
    USER_OR_PASSWORD_ERROR(402, "用户名或密码错误"),
    NO_AUTHORITY(403, "权限不足"),
    USER_NOT_EXITS(405, "用户不存在"),
    SYSTEM_ERROR(406, "系统错误");


    private final Integer code;
    private final String message;

    HttpCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
