package com.cst.xinhe.base.exception;

public enum ErrorCode {

    OK(1, "操作成功"),
    ERROR(0, "操作失败"),
    RESOURCE_NOT_FOUND(00000, "您所请求的资源不存在"),
    UNKNOWN_ERROR(40000, "服务器繁忙，请重试"),
    DATA_ERROR(40001, "加载失败，请重试"),
    NOT_FOUND(40004, "找不到文件"),
    INVALID_PARAM(40007, "无效参数"),
    NO_DATA(5000, "没有找到数据"),
    CONVERT_ERROR(5001, "格式刷数据失败！"),
    STATION_OFFLINE_ERROR(5002, "基站未在线，未能成功配置基站的网络参数！"),
    NO_BINDING_TIME_STANDARD(5003, "人员未绑定打卡时间标准！"),
    STATION_IS_NOT_EXIST_IN_T_UPDATE_IP(5004, "基站在更新IP中不存在！"),
    SEND_WS_OFFLINE_STATION_ERROR(5004, "推送掉线基站数量错误！"),
    SEND_VOICE_FAIL(5005,"发送语音数据失败"),
    FIND_STATION_IP_FAIL(5006, "获取基站的IP地址失败！"),
    DATA_TYPE_IS_ERROR(5007, "解析到的数据类型不匹配！"),
    SEND_INFO_TO_TERMINAL_FAIL(5008, "向终端发送信息失败！"),
    TERMINAL_AND_STAFF_NOT_BINDING(5009, "终端与人员未绑定！"),
    ;

    private int code;
    private String info;

    ErrorCode(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public static ErrorCode getByCode(int code) {
        ErrorCode[] values = ErrorCode.values();
        for (ErrorCode value : values) {
            if (value.getCode() == code)
                return value;
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
