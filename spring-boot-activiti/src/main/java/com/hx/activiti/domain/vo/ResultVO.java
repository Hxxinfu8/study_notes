package com.hx.activiti.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 返回结果
 * @author hx
 */
@Data
@Builder
public class ResultVO {
    private String message;
    private Integer code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static ResultVO success(Object o) {
        return ResultVO.builder().code(HttpStatus.OK.value()).message("成功").data(o).build();
    }
}
