package org.hx.netty.netty.constants;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Upoint0002
 */
@Data
@NoArgsConstructor
public class NettyVO {
    private String to;
    private String from;
    private String customerName;
    private String customerId;
    private String staffId;
    private String staffName;
    private String message;
    private Integer type;
    private Date createTime;

    public NettyVO(NettyCodeEnum codeEnum) {
        this.message = codeEnum.getType();
        this.type = codeEnum.getCode();
    }

    /**
     * 将json字符串转成对象
     *
     * @param message 信息
     * @return
     * @throws Exception
     */
    public static NettyVO strJson2Netty(String message) throws Exception {
        return StringUtil.isNullOrEmpty(message) ? null : JSONObject.parseObject(message, NettyVO.class);
    }

    /**
     * 将对象转成json字符串
     * @return String
     * @throws Exception
     */
    public static String toJson(NettyVO nettyVO){
        try {
            return JSONObject.toJSONString(nettyVO);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送消息
     * @param context
     * @param codeEnum
     */
    public static void sendMessage(Channel channel, NettyCodeEnum codeEnum) {
        channel.writeAndFlush(new TextWebSocketFrame(NettyVO.toJson(new NettyVO(codeEnum))));
    }

    public static void sendMessage(Channel channel, NettyVO vo) {
        channel.writeAndFlush(new TextWebSocketFrame(NettyVO.toJson(vo)));
    }

    public static void sendMessage(Channel channel, String msg) {
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    public static void setAttribute(ChannelHandlerContext context, AttributeKey<String> key, String value) {
        context.channel().attr(key).set(value);
    }

    public static void setAttribute(ChannelHandlerContext context, AttributeKey<Integer> key, Integer value) {
        context.channel().attr(key).set(value);
    }

    public static void setAttribute(ChannelHandlerContext context, AttributeKey<LocalDateTime> key, LocalDateTime value) {
        context.channel().attr(key).set(value);
    }

    public static void setAttribute(ChannelHandlerContext context, AttributeKey<NettyVO> key, NettyVO value) {
        context.channel().attr(key).set(value);
    }


}
