package com.hx.webflux.utils;

import com.alibaba.fastjson.JSONObject;
import com.hx.webflux.domain.constants.HttpCodeEnum;
import com.hx.webflux.domain.vo.ResultVO;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 响应工具类
 *
 * @author hx
 */
public class ServletUtil {
    public static Mono<Void> renderResponse(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.OK);
        String body = JSONObject.toJSONString(ResultVO.success(message));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    public static Mono<Void> renderResponse(ServerHttpResponse response, HttpCodeEnum httpCodeEnum) {
        String body = JSONObject.toJSONString(ResultVO.success(httpCodeEnum));
        response.setStatusCode(HttpStatus.OK);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
