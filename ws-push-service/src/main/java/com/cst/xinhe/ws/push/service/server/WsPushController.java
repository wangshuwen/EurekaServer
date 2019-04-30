package com.cst.xinhe.ws.push.service.server;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.ws.push.service.service.WsPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @program: WsPushController  服务接口
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 09:20
 **/
@RestController
@RequestMapping("ws-push-service/")
public class WsPushController {

    @Autowired
    private WsPushService wsPushService;

    @PostMapping("sendWebsocketServer")
    public String sendWebsocketServer(@RequestBody String jsonObject) throws IOException {
        wsPushService.sendWebsocketServer(jsonObject);
        return ResultUtil.jsonToStringSuccess();
    }

    @PostMapping("sendWSPersonNumberServer")
    public String sendWSPersonNumberServer(@RequestBody String jsonObject) throws IOException{
        wsPushService.sendWSPersonNumberServer(jsonObject);
        return ResultUtil.jsonToStringSuccess();
    }

    @PostMapping("sendWSServer")
    public String sendWSServer(@RequestBody String jsonObject) throws IOException{
        wsPushService.sendWSServer(jsonObject);
        return ResultUtil.jsonToStringSuccess();
    }

    @PostMapping("sendWSSiteServer")
    public String sendWSSiteServer(@RequestBody String jsonObject) throws IOException{
        wsPushService.sendWSSiteServer(jsonObject);
        return ResultUtil.jsonToStringSuccess();
    }
    @PostMapping("sendInfo")
    public String sendInfo(@RequestBody String jsonObject) throws IOException{
        wsPushService.sendInfo(jsonObject);
        return ResultUtil.jsonToStringSuccess();
    }



}

