package com.cst.xinhe.common.constant;

/**
 * @program: EurekaServer
 * @description: 常量类
 * @author: lifeng
 * @create: 2019-04-26 14:03
 **/
public class ConstantValue {
    //===============数据头===============
    public static final int MSG_HEADER_FREAME_HEAD = 0x6688;//, "帧头"),
    public static final int MSG_HEADER_COMMAND_ID_SEARCH = 0x0011;    //  , "查询"),
    public static final int MSG_HEADER_COMMAND_ID_CONTROL = 0x0012;  //  , "控制"),
    public static final int MSG_HEADER_COMMAND_ID_NULL = 0x0013;  //  , "语音"),
    public static final int MSG_HEADER_COMMAND_ID_REQUEST = 0x0015;   //  , "采集数据上报（设备到后台）"),
    public static final int MSG_HEADER_COMMAND_ID_RESPONSE = 0x0016;   //  , "应答"),

    public static final int MSG_HEADER_COMMAND_ID_HEARTBEAT = 0x001c; //  , "心跳数据"),
    public static final int MSG_HEADER_COMMAND_ID_SEND_VOICE = 0x001d; //  , "心跳数据"),


    //===============数据体===============
    public static final byte MSG_BODY_RESULT_SUCCESS = 0x55;  //  "消息体成功标志"),
    public static final byte MSG_BODY_RESULT_ERROR = 0x22;   //  "消息体失败标志"),
    public static final byte MSG_BODY_RESULT_NO_MEAN = -1; //  "无  意义标志"),
    public static final int MSG_BODY_NODE_NAME_HANDWARE_VERSION = 0x1001;   //  , "硬件版本号"),
    public static final int MSG_BODY_NODE_NAME_SOFTWARE_VERSION = 0x1002; //  , "软件版本号"),
    public static final int MSG_BODY_NODE_NAME_SELF_CHECK_RESULT = 0x1003;   //  , "自检结果"),
    public static final int MSG_BODY_NODE_NAME_SENSOR_DATA = 0x2001;  //  , "传感器数据"),
    public static final int MSG_BODY_NODE_NAME_LOCATOR_DATA = 0x2002;   //  , "定位数据"),
    public static final int MSG_BODY_NODE_NAME_VOICE_DATA = 0x2003;   //  , "语音数据"),
    public static final int MSG_BODY_NODE_NAME_UPDATE_IP = 0x2004;    //  , "IP更新上报"),
    public static final int MSG_BODY_NODE_NAME_CHECK_ONLINE = 0x2005;    //  , "节点在线，可以发起语音"),
    public static final int MSG_BODY_NODE_NAME_CONFIGURED = 0x2006;//  ,配置基站子网掩码和IP),
    public static final int MSG_BODY_NODE_NAME_CHECK_POWER = 0x2007;//  ,查询设备端电量),

    public static final int MSG_BODY_NODE_NAME_REAL_TIME_CALL = 0x2008;//  ,发起呼叫),
    public static final int MSG_BODY_NODE_NAME_MSG_READ_STATUS = 0x2009;//  ,单条语音阅读状态),

    public static final int MSG_BODY_NODE_NAME_HEARTBEAT = 0x3001;//  , "心跳"),
    public static final int MSG_BODY_NODE_NAME_POWER_STATUS = 0x3002;//  ,欠电提醒上传),
    public static final int MSG_BODY_NODE_NAME_GAS_LEVEL_STANDARD = 0x3003;// 气体等级标准设定
    public static final int MSG_BODY_NODE_NAME_SETTING_FREQUENCY = 0x3004;// 设置
    public static final int MSG_BODY_NODE_NAME_MAC_STATION = 0x3005;// 设置0x3005：wifi探针检测基站周围终端
    public static final int MSG_BODY_NODE_NAME_POSITION_SHOW = 0x3006;// 设置0x3006：临时位置显示
    public static final int MSG_BODY_NODE_NAME_PERSON_INFO_SEARCH = 0x3007;// 设置0x3007：个人信息查询

    //===============硬件故障代码===============
    public static final int MSG_BODY_HANDWARE_ERROR_WIFI = 0x0100; //  , "WIFI故障"),
    public static final int MSG_BODY_HANDWARE_ERROR_VOICE = 0x0200;   //  , "语音故障"),
    public static final int MSG_BODY_HANDWARE_ERROR_CO = 0x0300;  //  , "一氧化碳传感器故障"),
    public static final int MSG_BODY_HANDWARE_ERROR_CO2 = 0x0400;   //  , "二氧化碳传感器故障"),
    public static final int MSG_BODY_HANDWARE_ERROR_O2 = 0x0500;  //  , "氧气传感器故障"),
    public static final int MSG_BODY_HANDWARE_ERROR_CH4 = 0x0600;  //  , "甲烷传感器故障"),
    public static final int MSG_BODY_HANDWARE_ERROR_HUMITURE = 0x0700;  //  , "温湿度传感器故障"),
}
