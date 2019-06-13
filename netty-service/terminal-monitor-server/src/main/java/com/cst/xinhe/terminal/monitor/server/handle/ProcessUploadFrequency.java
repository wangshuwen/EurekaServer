package com.cst.xinhe.terminal.monitor.server.handle;

import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.netty.utils.NettyDataUtils;
import com.cst.xinhe.terminal.monitor.server.client.WebServiceClient;
import com.cst.xinhe.terminal.monitor.server.context.SpringContextUtil;
import com.cst.xinhe.terminal.monitor.server.request.SingletonClient;
import com.cst.xinhe.terminal.monitor.server.utils.SequenceIdGenerate;

/**
 * @program: demo
 * @description: 处理终端上传气体的频率
 * @author: lifeng
 * @create: 2019-02-18 13:37
 **/
public class ProcessUploadFrequency {

    private volatile static ProcessUploadFrequency processUploadFrequency;

//    private BaseStationService baseStationService;

    private WebServiceClient webServiceClient;

    public ProcessUploadFrequency() {
     //   this.baseStationService = SpringContextUtil.getBean(BaseStationServiceImpl.class);
        this.webServiceClient = SpringContextUtil.getBean(WebServiceClient.class);
    }

    public static ProcessUploadFrequency getSingletonProcessUploadFrequency() {
        if (processUploadFrequency == null) {
            synchronized (SingletonClient.class) {
                if (processUploadFrequency == null) {
                    processUploadFrequency = new ProcessUploadFrequency();
                }
            }
        }
        return processUploadFrequency;
    }

    public void process(RequestData customMsg){
        // 获取基站的ID
        Integer stationId = customMsg.getStationId();
        // 根据基站的ID获取到应该的上传频率
      //  double frequency = baseStationService.findFrequencyByStationId(stationId);
        double frequency  = webServiceClient.findFrequencyByStationId(stationId);
        Integer f = (int)frequency;


//        String str1 = frequencyOfResult[0];
//        String str2 = frequencyOfResult[1];

//        int i1 = Integer.parseInt(str1);
//        int i2 = Integer.parseInt(str2);

        customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_CONTROL);

        customMsg.setNdName(ConstantValue.MSG_BODY_NODE_NAME_SETTING_FREQUENCY);

        byte[] body = new byte[2];
        byte[] t_body = NettyDataUtils.intToByteArray(f);
        body[0] = t_body[2];
        body[1] = t_body[3];

        customMsg.setSequenceId(SequenceIdGenerate.getSequenceId());
        customMsg.setBody(body);
        customMsg.setLength(36);
        ResponseData responseData = new ResponseData();
        responseData.setCustomMsg(customMsg);
        //发送具体的设置内容
        SingletonClient.getSingletonClient().sendCmd(responseData);
    }
}
