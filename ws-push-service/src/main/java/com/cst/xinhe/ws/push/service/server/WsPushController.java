package com.cst.xinhe.ws.push.service.server;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.ws.push.service.service.WsPushService;
import com.cst.xinhe.ws.push.service.ws.WSServer;
import com.cst.xinhe.ws.push.service.ws.WSSiteServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @program: WsPushController  服务接口
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 09:20
 **/
@RestController
//@RequestMapping("ws-push-service/")
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
    @PostMapping("sendWSBigDataServer")
    public String sendWSBigDataServer(@RequestBody String jsonObject) throws IOException{
        wsPushService.sendWSBigDataServer(jsonObject);
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

    @GetMapping("getWSSiteServerOrgId")
    public Integer getOrgId(){

        return WSSiteServer.orgId;
    }



    @GetMapping("getWSSiteServerZoneId")
    public Integer getZoneId(){


        return WSSiteServer.zoneId;
    }


    @GetMapping("getWSServerOrgId")
    public Integer getWSServerOrgId(){
        return WSServer.orgId;
    }

    @GetMapping("getWSServerZoneId")
    public Integer getWSServerZoneId(){
        return WSServer.zoneId;
    }

    @GetMapping("setOrdId")
    public void setOrdId(@RequestParam Integer o){
        WSServer.orgId = o;
    }
    @GetMapping("setZoneId")
    public void setZoneId(@RequestParam Integer o){
        WSServer.zoneId = o;
    }


    @GetMapping(value = "setOrgIdIsNull")
    public void setOrgIdIsNull(){
        WSServer.orgId = null;
    }


    @GetMapping(value = "setZoneIdIsNull")
    public void setZoneIdIsNull(){
        WSServer.zoneId = null;
    }

}

