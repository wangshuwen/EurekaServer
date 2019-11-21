package com.cst.xinhe.web.service.drool.droolController;    /**
 * @author wangshuwen
 * @Description:
 * @Date 2019-11-08/15:24
 */

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.web.service.drool.entity.Message;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:        wangshuwen
 * @Description:
 * @CreateDate:     2019-11-08 13:31
 * @Version:        1.0
 */
@RestController
@RequestMapping("/drools/")
public class TestController {
    @Autowired
    private KieSession kieSession;

    @GetMapping("test")
    public String test(@RequestParam("command") String command){


        Message message = new Message();
        message.setMsg("hello");
        message.setStatus("0");
        message.setSize("0");
        message.setCommand(1);
        kieSession.insert(message);
        int i = kieSession.fireAllRules();
        System.out.println("触发了"+i+"条规则");



        return ResultUtil.jsonToStringSuccess(command);














       /* if(command==1){
            return ResultUtil.jsonToStringSuccess("警报！警报！ 一氧化碳超标了");
        }
        if(command==2){
            return ResultUtil.jsonToStringSuccess("警报！警报！ 甲烷超标了");
        }
        if(command==3){
            return ResultUtil.jsonToStringSuccess("警报！警报！ 氧气过低了");
        }
        if(command==4){
            return ResultUtil.jsonToStringSuccess("警报！警报！ 温度过高了");
        }
        if(command==5){
            return ResultUtil.jsonToStringSuccess("警报！警报！湿度过低了");
        }

        return ResultUtil.jsonToStringSuccess("OK");*/
    }



}
