package com.cst.xinhe.stationpartition.service.controller;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.netty.utils.NettyDataUtils;
import com.cst.xinhe.common.utils.SequenceIdGenerate;
import com.cst.xinhe.stationpartition.service.client.StationMonitorServerClient;
import com.cst.xinhe.stationpartition.service.client.callback.StationMonitorServerClientFallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2018/11/26/14:51
 */
@RestController
public class BaseStationSettingController {

    @Autowired
    StationMonitorServerClient stationMonitorServerClient;
    @GetMapping("/setting")
    public String settingBaseStation(@RequestParam("ip") String ip, @RequestParam("network") String network) {

        ResponseData responseData = new ResponseData();

        RequestData requestData = new RequestData();
        requestData.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
        requestData.setLength(42);
        requestData.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_CONTROL);
        requestData.setSequenceId(SequenceIdGenerate.getSequenceId());
        requestData.setNdName(8198);
        requestData.setTerminalIp("1.1");
        requestData.setStationIp("1.1");

        //封装ip和子网掩码 8字节
        byte[] body = new byte[8];
        String[] ips = ip.split("\\.");
        String[] nets = network.split("\\.");
        body[0] = NettyDataUtils.intToByteArray(Integer.parseInt(ips[0]))[3];
        body[1]= NettyDataUtils.intToByteArray(Integer.parseInt(ips[1]))[3];
        body[2]=NettyDataUtils.intToByteArray(Integer.parseInt(ips[2]))[3];
        body[3]=NettyDataUtils.intToByteArray(Integer.parseInt(ips[3]))[3];
        body[4]=NettyDataUtils.intToByteArray(Integer.parseInt(nets[0]))[3];
        body[5]=NettyDataUtils.intToByteArray(Integer.parseInt(nets[1]))[3];
        body[6]=NettyDataUtils.intToByteArray(Integer.parseInt(nets[2]))[3];
        body[7]=NettyDataUtils.intToByteArray(Integer.parseInt(nets[3]))[3];
        requestData.setBody(body);
        responseData.setCustomMsg(requestData);

//        SingletonClient.getSingletonClient().sendCmd(responseData);
        stationMonitorServerClient.sendCmd(responseData);
        return ResultUtil.jsonToStringSuccess(responseData);
    }
}
