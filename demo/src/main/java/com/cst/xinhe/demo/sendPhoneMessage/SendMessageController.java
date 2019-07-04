package com.cst.xinhe.demo.sendPhoneMessage;

import java.util.HashMap;
import java.util.Map;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.RestController;


/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/7/3/17:39
 */
@RestController
@Api(value = "SendMessageController", tags = "发送短信")
@RequestMapping("sms/")
public class SendMessageController {



    @ApiOperation(value = "获取最近的气体信息", notes = "解决数据开始空白问题")
    @GetMapping("sendMessage")
    public void getRecentlyGasInfo() {

        String host = "http://yzx.market.alicloudapi.com";
        String path = "/yzx/sendSms";
        String method = "POST";
        //这里填写你的appcode
        String appcode = "ebd448100b074ddb90818160d19b0ad2";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        //这里填写你要发送的手机号码
        querys.put("mobile", "15297921025");
        querys.put("param", "code:1234");
        //querys.put("param", "这里填写你和商家定义的变量名称和变量值填写格式看上一行代码");
        //这里填写你和商家商议的模板
        querys.put("tpl_id", "TP1710262");
        Map<String, String> bodys = new HashMap<>();




        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());

            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public static void main(String[] args) {

    }

}
