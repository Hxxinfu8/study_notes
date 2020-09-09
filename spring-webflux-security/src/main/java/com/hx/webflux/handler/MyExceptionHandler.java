package com.hx.webflux.handler;

import com.hx.webflux.domain.constants.HttpCodeEnum;
import com.hx.webflux.domain.vo.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author hx
 */
@RestControllerAdvice
public class MyExceptionHandler {
    /**
     * 用户不存在异常处理
     *
     * @return ResultVO
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultVO userNotExitsException() {
        return ResultVO.success(HttpCodeEnum.USER_NOT_EXITS);
    }

    /**
     * 用户名密码错误异常处理
     *
     * @return ResultVO
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultVO badCredentialsException() {
        return ResultVO.success(HttpCodeEnum.USER_OR_PASSWORD_ERROR);
    }
}
