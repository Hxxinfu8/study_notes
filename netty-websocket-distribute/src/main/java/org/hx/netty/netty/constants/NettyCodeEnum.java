package org.hx.netty.netty.constants;

/**
 * @author Upoint0002
 */
public enum NettyCodeEnum {
    ERROR(500, "数据错误"),
    AUTH_ERROR(501, "验证失败"),
    FILE_SEND(999, "文件传输"),
    CLIENT_ONLINE(1000, "访客上线"),
    STAFF_ONLINE(1001, "客服上线"),
    BROADCAST(1002, "访客广播"),
    SINGLE_SEND(1003, "点对点发送"),
    STAFF_WATCH(1004, "0"),
    STAFF_NO_WATCH(1005, "客服断开监听"),
    CLIENT_ACTIVE(1006, "访客请求建立连接"),
    CLIENT_OFFLINE(1007, "访客请求断开连接"),
    DOCKING(1008, "客服对接"),
    DOCK_FAIL(1009, "暂无客服"),
    OFFLINE(1010, "已下线"),
    TRANSFER_BOT(1011, "转接机器客服"),
    CLIENT_ACTIVE_SUCCESS(1012, "访客请求建立连接成功"),
    CLIENT_HAS_DOCK(1013, "访客已被其他客服接受"),
    WAITING_QUEUE(1014, "等待队列"),
    DOCK_SUCCESS(1016, "客服对接成功"),
    STAFF_SEAT_FULL(1025,"人工客服坐席已满"),
    //人工客服转接状态码
    OTHER_STAFF_LIST(1015, "获取除自身外其它人工客服列表"),
    REQUEST_TRANSFER_STAFF(1017, "请求转接到其它人工客服"),
    HAVE_STAFF_TRANSFER(1018, "有人工客服转接请求"),
    WAITING_OTHER_STAFF_ACCEPT(1019, "等待已转接的人工客服接受"),
    OTHER_STAFF_TRANSFER_SUCCESS(1020, "人工客服转接成功"),
    CANCEL_TRANSFER_STAFF(1033, "取消转接到其它人工客服"),
    CANCEL_TRANSFER_STAFF_SUCCESS(1034, "取消转接到其它人工客服成功"),
    CLIENT_OUT_OF_TIME(1021, "访客超时提示"),
    STAFF_OUT_OF_TIME(1022, "客服超时提示"),
    CHOOSE_SKILL_GROUP(1023, "请选择技能组"),
    CONTACT_SKILL_GROUP(1024, "转接技能组"),
    ONLINE_QUEUE(1026, "访客在线列表"),
    WAITING_TIMEOUT(1027,"用户排队等待超时"),
    USER_RESPONDING_TIMEOUT(1028,"用户长时间不活跃"),
    CONNECT_RESET(1030, "访客webSocket重连"),
    WAITING_QUEUE_TIMEOUT(1031, "访客等待"),
    WAITING_QUEUE_OFFLINE(1032, "等待列表下线");


    private Integer code;
    private String type;

    NettyCodeEnum(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
