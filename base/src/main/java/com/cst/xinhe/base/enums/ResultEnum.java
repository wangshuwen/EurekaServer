package com.cst.xinhe.base.enums;

public enum ResultEnum {
    UNKNOW_ERROR(-100, "未知错误！"),
    ACCESS_WITHOUT_PERMISSION(-101, "没权限访问！"),
    TOKEN_INVALIDATION(-102, "令牌失效,请重新登录！"),
    NULL_POINT_ERROR(501, "空指针异常！"),
    SUCCESS(100, "处理成功！"),
    FAILED(101, "处理失败！"),
    NO_PERMISSION(102, "没有权限！"),
    REJECT_REQUEST(103, "拒绝请求！"),
    LOGIN_FAIL(104, "登录失败！"),
    NOT_IS_STAFF(-1, "非本公司员工！"),
    IS_NOT_LOGIN(105, "未登录，请先登录！"),
    TOKEN_ERROR(106, "token错误，请重试！"),
    UN_ANTUORIZED(107, "Unauthorized，操作失败，无操作权限！"),
    USER_IS_LOCKED(108, "账号已被冻结！"),
    PWD_IS_ERROR(109, "密码错误！"),
    USER_NOT_EXIST(109, "用户不存在！"),
    PAGE_NOT_FOUND(404, "页面走丢了！"),
    WEBSOCKET_SEND_ERROR(110, "websocket error"),
    DATA_NOT_FOUND(111, "暂无数据!"),
    DEPT_NOT_FOUND(112, "该部门不存在!"),
    REQUEST_DATA_IS_NULL(113, "请求数据为空!"),
    DATA_IS_NOT_NULL(114, "该层级下面存在数据不能删除!"),
    SEND_VOICE_ERROR(115, "语音发送失败!"),
    CHECK_ONLINE_FAIL(116, "检测终端不在线!"),
    ADD_STATION_FAIL(117, "新增基站失败!"),
    UPDATE_INFO_ERROR(118, "更新基站信息失败!"),
    DELETE_STATION_ERROR(119, "删除基站失败!"),
    ADD_ZONE_ERROR(120, "添加大区失败!"),
    UPDATE_ZONE_ERROR(121, "更新大区失败!"),
    DELETE_ZONE_ERROR(122, "删除大区失败!"),
    BIND_ATTENDANCE_STATION_ERROR(123, "绑定考勤基站失败，请重试!"),
    BASE_STATION_NUM_EXIST(124, "基站ID已存在，请重新录入"),
    ADD_STANDARD_FAIL(125, "添加标准失败"),
    UPDATE_STANDARD_FAIL(126, "更新标准失败"),
    DELETE_STANDARD_FAIL(127, "删除标准失败"),
    BINDING_GAS_LEVEL_FAIL(128, "基站绑定气体标准失败"),
    BINDING_STATION_MOUTH_FAIL(129, "基站绑定井口失败"),
    UPDATE_TIME_STANDARD_FAIL(130, "更新时间标准失败"),
    DELETE_TIME_STANDARD_FAIL(131, "删除时间标准失败"),
    INSERT_TIME_STANDARD_FAIL(132, "添加时间标准失败"),
    GET_MALFUNCTION_COUNT_FAIL(133, "获取自检未处理数量失败"),
    SETTING_STATION_UPLOAD_FREQUENCY_FAIL(134, "设置上传频率失败"),
    DEL_PARTITION_FAIL(135, "该区域下包含部署基站，不允许删除"),
    DEL_ORG_FAIL(136, "该组织下已存在员工，不允许删除"),
    DEL_TIME_STANDARD_FAIL(137, "该组织下已存在员工，不允许删除"),
    TERMINAL_IS_OFF_LINE(138,"该终端不在线"),
    PERMISSION_CHECK_FAIL(139,"权限验证失败"),
    ADD_AREA_FAIL(140, "添加区域失败，请返回区域列表重新设置!"),
    UPDATE_AREA_FAIL(141, "编辑区域失败，请返回区域列表重新设置!"),
    STATION_CANT_SETTING_TWO_STATUS(142, "基站不能同时被设置为考勤和井口基站!"),
    STATION_FAILED_TO_ACQUIRE_NUMBER_OF_NEARBY_TERMINALS(143, "基站获取附近终端数量失败！"),
    STATION_IS_EXISTS(144, "基站ID已经存在！"),
    TERMINAL_IS_EXISTS(145, "终端ID已经存在！"),
    TerminalMonitorFallback(146, "服务向终端发送消息失败！"),
    GET_CHANNEL_FAIL(147, "调用服务获取客户端数量失败！"),
    SEND_TO_SERVER_CHAT_MSG_FAIL(148, "调用服务发送语音数据失败！"),
    CALL_REMOTE_SERVER_FAIL(149, "调用远程服务失败！"),
    ;

    private Integer code;
    private String msg;


    ResultEnum(Integer code) {
        this.code = code;
    }

    ResultEnum(String msg) {
        this.msg = msg;
    }

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
