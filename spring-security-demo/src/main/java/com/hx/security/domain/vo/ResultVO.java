package com.hx.security.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hx.security.domain.constants.HttpCodeEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 返回值
 *
 * @author hx
 */
@Data
@Builder
public class ResultVO {
    private String message;
    private Integer code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static ResultVO success() {
        return ResultVO.builder().code(HttpStatus.OK.value()).message("成功").build();
    }

    public static ResultVO success(Object o) {
        return ResultVO.builder().code(HttpStatus.OK.value()).message("成功").data(o).build();
    }

    public static ResultVO success(String message) {
        return ResultVO.builder().code(HttpStatus.OK.value()).message(message).build();
    }

    public static ResultVO success(String message, Integer code, Object data) {
        return ResultVO.builder()
                .code(code).message(message).data(data).build();
    }

    public static ResultVO success(HttpCodeEnum httpCodeEnum) {
        return ResultVO.builder().code(httpCodeEnum.code()).message(httpCodeEnum.message()).build();
    }

}
