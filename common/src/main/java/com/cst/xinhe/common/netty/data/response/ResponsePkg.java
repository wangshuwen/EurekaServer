package com.cst.xinhe.common.netty.data.response;


import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.utils.NettyDataUtils;

import java.util.Calendar;

/**
 * @ClassName Response
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/28 13:20
 * @Vserion v0.0.1
 */

public class ResponsePkg {

    /**
     * @param
     * @return byte[]
     * @description 封装响应数据的  方法  数据帧头 +  数据体的 code
     * @date 16:09 2018/9/28
     * @auther lifeng
     **/
    public byte[] dataResponse(RequestData msg) {

        if (msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_CHECK_ONLINE
                || msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_REAL_TIME_CALL
                || msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_POWER_STATUS
                || msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_CHECK_POWER) {

            byte[] data = new byte[34];

            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            data[0] = type[2];
            data[1] = type[3];

            byte[] tId = NettyDataUtils.intToByteArray(msg.getTerminalId());
            data[2] = tId[0];
            data[3] = tId[1];
            data[4] = tId[2];
            data[5] = tId[3];
            byte[] sId = NettyDataUtils.intToByteArray(msg.getStationId());
            data[6] = sId[0];
            data[7] = sId[1];
            data[8] = sId[2];
            data[9] = sId[3];
            data[10] = (byte) msg.getTerminalIp1();
            data[11] = (byte) msg.getTerminalIp2();
            data[12] = (byte) msg.getStationIp1();
            data[13] = (byte) msg.getStationIp2();
            byte[] terminalPort = NettyDataUtils.intToByteArray(msg.getTerminalPort());
            data[14] = terminalPort[2];
            data[15] = terminalPort[3];

            byte[] stationPort = NettyDataUtils.intToByteArray(msg.getStationPort());
            data[16] = stationPort[2];
            data[17] = stationPort[3];

            byte[] length = NettyDataUtils.intToByteArray(msg.getLength());
            data[18] = length[2];
            data[19] = length[3];

            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            data[20] = cmd[2];
            data[21] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            data[22] = seq[2];
            data[23] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH) + 1;//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            data[24] = (byte) rty;
            data[25] = (byte) month;
            data[26] = (byte) day;
            data[27] = (byte) hour;
            data[28] = (byte) minute;
            data[29] = (byte) second;
            data[30] = msg.getResult();
            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            data[32] = ndName[2];
            data[33] = ndName[3];

            return data;

        }

        //配置基站的子网掩码和IP
        if(msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_CONFIGURED){
            byte[] data = new byte[42];

            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            data[0] = type[2];
            data[1] = type[3];
            byte[] length = NettyDataUtils.intToByteArray(msg.getLength());
            data[18] = length[2];
            data[19] = length[3];

            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            data[20] = cmd[2];
            data[21] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            data[22] = seq[2];
            data[23] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH) + 1;//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            data[24] = (byte) rty;
            data[25] = (byte) month;
            data[26] = (byte) day;
            data[27] = (byte) hour;
            data[28] = (byte) minute;
            data[29] = (byte) second;
            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            data[32] = ndName[2];
            data[33] = ndName[3];
            byte[] ipsNets=msg.getBody();
            data[34] = ipsNets[0];
            data[35] = ipsNets[1];
            data[36] = ipsNets[2];
            data[37] = ipsNets[3];
            data[38] = ipsNets[4];
            data[39] = ipsNets[5];
            data[40] = ipsNets[6];
            data[41] = ipsNets[7];

            return data;
        }

        if(msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_SETTING_FREQUENCY){
            byte[] data = new byte[msg.getLength()];

            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            data[0] = type[2];
            data[1] = type[3];
            byte[] length = NettyDataUtils.intToByteArray(msg.getLength());
            data[18] = length[2];
            data[19] = length[3];

            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            data[20] = cmd[2];
            data[21] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            data[22] = seq[2];
            data[23] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH) + 1;//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            data[24] = (byte) rty;
            data[25] = (byte) month;
            data[26] = (byte) day;
            data[27] = (byte) hour;
            data[28] = (byte) minute;
            data[29] = (byte) second;
            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            data[32] = ndName[2];
            data[33] = ndName[3];
            byte[] body = msg.getBody();
            data[34] = body[0];
            data[35] = body[1];

            return data;
        }

//        int len = msg.getLength();  //返回终端 TODO 语音下发  ，， 返回加部门和分组
        //返回封装 返回心跳结果
        if (msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_HEARTBEAT) {
            byte[] resp = new byte[30];
            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            resp[0] = type[2];
            resp[1] = type[3];
            byte[] tId = NettyDataUtils.intToByteArray(msg.getTerminalId());
            resp[2] = tId[0];
            resp[3] = tId[1];
            resp[4] = tId[2];
            resp[5] = tId[3];
            byte[] sId = NettyDataUtils.intToByteArray(msg.getStationId());
            resp[6] = sId[0];
            resp[7] = sId[1];
            resp[8] = sId[2];
            resp[9] = sId[3];
            resp[10] = (byte) msg.getTerminalIp1();
            resp[11] = (byte) msg.getTerminalIp2();
            resp[12] = (byte) msg.getStationIp1();
            resp[13] = (byte) msg.getStationIp2();
            byte[] length = NettyDataUtils.intToByteArray(msg.getLength());
            resp[14] = length[2];
            resp[15] = length[3];


            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            resp[16] = cmd[2];
            resp[17] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            resp[18] = seq[2];
            resp[19] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH) + 1;//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            resp[20] = (byte) rty;
            resp[21] = (byte) month;
            resp[22] = (byte) day;
            resp[23] = (byte) hour;
            resp[24] = (byte) minute;
            resp[25] = (byte) second;
            resp[26] = msg.getResult();

            resp[27] = (byte) 0x00;

            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            resp[28] = ndName[2];
            resp[29] = ndName[3];
//            for (int i = 0; i < resp.length; i++)
//                System.out.printf(" 0x%02x", resp[i]);
            return resp;
        }
        //封装 返回 气体信息 收到结果
        if (msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_SENSOR_DATA) {
            byte[] resp = new byte[msg.getLength()];
            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            resp[0] = type[2];
            resp[1] = type[3];
            byte[] tId = NettyDataUtils.intToByteArray(msg.getTerminalId());
            resp[2] = tId[0];
            resp[3] = tId[1];
            resp[4] = tId[2];
            resp[5] = tId[3];
            byte[] sId = NettyDataUtils.intToByteArray(msg.getStationId());
            resp[6] = sId[0];
            resp[7] = sId[1];
            resp[8] = sId[2];
            resp[9] = sId[3];
            resp[10] = (byte) msg.getTerminalIp1();
            resp[11] = (byte) msg.getTerminalIp2();
            resp[12] = (byte) msg.getStationIp1();
            resp[13] = (byte) msg.getStationIp2();
            byte[] terminalPort = NettyDataUtils.intToByteArray(msg.getTerminalPort());
            resp[14] = terminalPort[2];
            resp[15] = terminalPort[3];
            byte[] stationPort = NettyDataUtils.intToByteArray(msg.getStationPort());
            resp[16] = stationPort[2];
            resp[17] = stationPort[3];
            byte[] length = NettyDataUtils.intToByteArray(34);
            resp[18] = length[2];
            resp[19] = length[3];

            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            resp[20] = cmd[2];
            resp[21] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            resp[22] = seq[2];
            resp[23] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH)+1;//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            resp[24] = (byte) rty;
            resp[25] = (byte) month;
            resp[26] = (byte) day;
            resp[27] = (byte) hour;
            resp[28] = (byte) minute;
            resp[29] = (byte) second;
            resp[30] = msg.getResult();

            resp[31] = msg.getNodeCount();

            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            resp[32] = ndName[2];
            resp[33] = ndName[3];
//            for (int i = 0; i < resp.length; i++)
//                System.out.printf(" 0x%02x", resp[i]);
            return resp;
        }

        //封装 更新终端IP 方法 数据
        if (msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_UPDATE_IP) {
            byte[] resp = new byte[msg.getLength()];
            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            resp[0] = type[2];
            resp[1] = type[3];
            byte[] tId = NettyDataUtils.intToByteArray(msg.getTerminalId());
            resp[2] = tId[0];
            resp[3] = tId[1];
            resp[4] = tId[2];
            resp[5] = tId[3];
            byte[] sId = NettyDataUtils.intToByteArray(msg.getStationId());
            resp[6] = sId[0];
            resp[7] = sId[1];
            resp[8] = sId[2];
            resp[9] = sId[3];
            resp[10] = (byte) msg.getTerminalIp1();
            resp[11] = (byte) msg.getTerminalIp2();
            resp[12] = (byte) msg.getStationIp1();
            resp[13] = (byte) msg.getStationIp2();
            byte[] terminalPort = NettyDataUtils.intToByteArray(msg.getTerminalPort());
            resp[14] = terminalPort[2];
            resp[15] = terminalPort[3];
            byte[] stationPort = NettyDataUtils.intToByteArray(msg.getStationPort());
            resp[16] = stationPort[2];
            resp[17] = stationPort[3];
            byte[] length = NettyDataUtils.intToByteArray(msg.getLength());
            resp[18] = length[2];
            resp[19] = length[3];

            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            resp[20] = cmd[2];
            resp[21] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            resp[22] = seq[2];
            resp[23] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH) + 1;//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            resp[24] = (byte) rty;
            resp[25] = (byte) month;
            resp[26] = (byte) day;
            resp[27] = (byte) hour;
            resp[28] = (byte) minute;
            resp[29] = (byte) second;
            resp[30] = msg.getResult();

            resp[31] = msg.getNodeCount();

            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            resp[32] = ndName[2];
            resp[33] = ndName[3];
//            byte[] body = msg.getBody();
//            System.arraycopy(body, 0, resp, 34, (body.length));
            return resp;
        }

        //封装 设置气体标准协议
        if (msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_GAS_LEVEL_STANDARD) {
            byte[] resp = new byte[msg.getLength()];
            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            resp[0] = type[2];
            resp[1] = type[3];
            byte[] tId = NettyDataUtils.intToByteArray(msg.getTerminalId());
            resp[2] = tId[0];
            resp[3] = tId[1];
            resp[4] = tId[2];
            resp[5] = tId[3];
            byte[] sId = NettyDataUtils.intToByteArray(msg.getStationId());
            resp[6] = sId[0];
            resp[7] = sId[1];
            resp[8] = sId[2];
            resp[9] = sId[3];
            resp[10] = (byte) msg.getTerminalIp1();
            resp[11] = (byte) msg.getTerminalIp2();
            resp[12] = (byte) msg.getStationIp1();
            resp[13] = (byte) msg.getStationIp2();
            byte[] terminalPort = NettyDataUtils.intToByteArray(msg.getTerminalPort());
            resp[14] = terminalPort[2];
            resp[15] = terminalPort[3];
            byte[] stationPort = NettyDataUtils.intToByteArray(msg.getStationPort());
            resp[16] = stationPort[2];
            resp[17] = stationPort[3];
            byte[] length = NettyDataUtils.intToByteArray(msg.getLength());
            resp[18] = length[2];
            resp[19] = length[3];

            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            resp[20] = cmd[2];
            resp[21] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            resp[22] = seq[2];
            resp[23] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH) + 1;//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            resp[24] = (byte) rty;
            resp[25] = (byte) month;
            resp[26] = (byte) day;
            resp[27] = (byte) hour;
            resp[28] = (byte) minute;
            resp[29] = (byte) second;
            resp[30] = msg.getResult();

            resp[31] = msg.getNodeCount();

            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            resp[32] = ndName[2];
            resp[33] = ndName[3];
            byte[] body = msg.getBody();
            System.arraycopy(body, 0, resp, 34, (body.length));
            return resp;
        }



        //返回 上传的语音收到结果
        if (msg.getNdName() == ConstantValue.MSG_BODY_NODE_NAME_VOICE_DATA) {
            byte[] resp = new byte[msg.getLength()];
            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            resp[0] = type[2];
            resp[1] = type[3];
            byte[] tId = NettyDataUtils.intToByteArray(msg.getTerminalId());
            resp[2] = tId[0];
            resp[3] = tId[1];
            resp[4] = tId[2];
            resp[5] = tId[3];
            byte[] sId = NettyDataUtils.intToByteArray(msg.getStationId());
            resp[6] = sId[0];
            resp[7] = sId[1];
            resp[8] = sId[2];
            resp[9] = sId[3];
            resp[10] = (byte) msg.getTerminalIp1();
            resp[11] = (byte) msg.getTerminalIp2();
            resp[12] = (byte) msg.getStationIp1();
            resp[13] = (byte) msg.getStationIp2();
            byte[] terminalPort = NettyDataUtils.intToByteArray(msg.getTerminalPort());
            resp[14] = terminalPort[2];
            resp[15] = terminalPort[3];
            byte[] stationPort = NettyDataUtils.intToByteArray(msg.getStationPort());
            resp[16] = stationPort[2];
            resp[17] = stationPort[3];
            byte[] length = NettyDataUtils.intToByteArray(msg.getLength());
            resp[18] = length[2];
            resp[19] = length[3];


            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            resp[20] = cmd[2];
            resp[21] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            resp[22] = seq[2];
            resp[23] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH);//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            resp[24] = (byte) rty;
            resp[25] = (byte) month;
            resp[26] = (byte) day;
            resp[27] = (byte) hour;
            resp[28] = (byte) minute;
            resp[29] = (byte) second;
            resp[30] = msg.getResult();

            resp[31] = (byte) 0x00;

            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            resp[32] = ndName[2];
            resp[33] = ndName[3];
            return resp;
        }


        /*************************************************************************************************
         *
         * cmd 问题
         */
        //向下  终端发送语音信息 数据封装
        if (msg.getCmd() == ConstantValue.MSG_HEADER_COMMAND_ID_SEND_VOICE) {
            int len = msg.getLength();
            byte[] resp = new byte[len];
            byte[] type = NettyDataUtils.intToByteArray(msg.getType());
            resp[0] = type[2];
            resp[1] = type[3];
            byte[] tId = NettyDataUtils.intToByteArray(msg.getTerminalId());
            resp[2] = tId[0];
            resp[3] = tId[1];
            resp[4] = tId[2];
            resp[5] = tId[3];
            byte[] sId = NettyDataUtils.intToByteArray(msg.getStationId());
            resp[6] = sId[0];
            resp[7] = sId[1];
            resp[8] = sId[2];
            resp[9] = sId[3];
            resp[10] = (byte) msg.getTerminalIp1();
            resp[11] = (byte) msg.getTerminalIp2();
            resp[12] = (byte) msg.getStationIp1();
            resp[13] = (byte) msg.getStationIp2();
            byte[] terminalPort = NettyDataUtils.intToByteArray(msg.getTerminalPort());
            resp[14] = terminalPort[2];
            resp[15] = terminalPort[3];
            byte[] stationPort = NettyDataUtils.intToByteArray(msg.getStationPort());
            resp[16] = stationPort[2];
            resp[17] = stationPort[3];
            byte[] length = NettyDataUtils.intToByteArray(len);
            resp[18] = length[2];
            resp[19] = length[3];


            byte[] cmd = NettyDataUtils.intToByteArray(msg.getCmd());
            resp[20] = cmd[2];
            resp[21] = cmd[3];
            byte[] seq = NettyDataUtils.intToByteArray(msg.getSequenceId());
            resp[22] = seq[2];
            resp[23] = seq[3];

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//获取年份
            int rty = year - 2000;
            int month = cal.get(Calendar.MONTH);//获取月份
            int day = cal.get(Calendar.DATE);//获取日
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒

            resp[24] = (byte) rty;
            resp[25] = (byte) month;
            resp[26] = (byte) day;
            resp[27] = (byte) hour;
            resp[28] = (byte) minute;
            resp[29] = (byte) second;
            resp[30] = msg.getResult();

            resp[31] = (byte) 0x00;

            byte[] ndName = NettyDataUtils.intToByteArray(msg.getNdName());
            resp[32] = ndName[2];
            resp[33] = ndName[3];

            byte[] count = NettyDataUtils.intToByteArray(msg.getCount());
            resp[34] = count[2];
            resp[35] = count[3];

            byte[] body = msg.getBody();

            System.arraycopy(body, 0, resp, 36, (body.length));
            return resp;
        }

        return null;
    }
}
