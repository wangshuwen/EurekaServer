package com.cst.xinhe.station.monitor.server.client;

import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.station.monitor.server.client.callback.TerminalMonitorFallback;
import com.cst.xinhe.station.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-28 14:34
 **/
@FeignClient(value = "terminal-monitor-server",
        configuration = FeignConfig.class,
        fallback = TerminalMonitorFallback.class,
        url = "http://127.0.0.1:8766/")
//@RequestMapping("terminal-monitor-server")
public interface TerminalMonitorClient {

    /**
     * 向终端服务发送响应信息
     * @param responseData
     * @return
     */
    @PostMapping("/sendResponseData")
    String sendResponseData(@RequestBody() ResponseData responseData);

    /**
     * 若存在IP和端口，则返回true
     * @param ipPort
     * @return
     */
    @GetMapping("/getChannelByIpPort")
    Boolean getChanelByName(@RequestParam("ipPort") String ipPort);

    @GetMapping("getSequenceId")
    Integer getSequenceId();

    @GetMapping("getBatteryByTerminalNum")
    Integer getBatteryByTerminalNum(@RequestParam("terminalNum") Integer terminalNum);
}
