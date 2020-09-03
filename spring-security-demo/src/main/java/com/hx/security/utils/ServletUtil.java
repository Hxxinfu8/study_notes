package com.hx.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.hx.security.domain.constants.HttpCodeEnum;
import com.hx.security.domain.vo.ResultVO;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 客户端工具类
 *
 * @author hx
 */
public class ServletUtil {

    public static void renderResponse(HttpServletResponse response, String body) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(body);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renderResponse(HttpServletResponse response, HttpCodeEnum httpCodeEnum) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter printWriter = response.getWriter();
            String body = JSONObject.toJSONString(ResultVO.success(httpCodeEnum));
            printWriter.write(body);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
