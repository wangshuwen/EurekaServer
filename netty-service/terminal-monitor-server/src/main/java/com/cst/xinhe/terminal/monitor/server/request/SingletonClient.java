package com.cst.xinhe.terminal.monitor.server.request;

import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeServiceException;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.terminal.monitor.server.channel.ChannelMap;
import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @program: demo
 * @description: 单例Client响应类
 * @author: lifeng
 * @create: 2019-01-15 15:10
 **/
public class SingletonClient {

    private static Logger logger = LogManager.getLogger(SingletonClient.class);

    //从配置文件获取ip前缀
    private String ip_prefix;

    private volatile static SingletonClient client;

    private SingletonClient(){
        this.ip_prefix = "192.168.";
    }

    public static SingletonClient getSingletonClient() {
        if (client == null) {
            synchronized (SingletonClient.class) {
                if (client == null) {
                    client = new SingletonClient();
                }
            }
        }
        return client;
    }
    /**
     * @param
     * @return int
     * @description 模拟客户端，netty server 向客户端发送返回的数据
     * 通过参数 获取到 连接中的key ，从ChannelMap中获取 Channel
     * 向Channel中回写数据
     * @date 14:47 2018/11/26
     * @auther lifeng
     **/
    public int sendCmd(ResponseData responseData) {

        String t_ip = responseData.getCustomMsg().getTerminalIp();

//        int channelNum = ChannelMap.channelNum;

        String ip = ip_prefix + t_ip + ":" + responseData.getCustomMsg().getTerminalPort();
        Channel channel = ChannelMap.getChannelByName(ip);
        logger.info("终端发送数据完成！");
        try {
            channel.writeAndFlush(responseData);
        } catch (Exception e){
            throw new RuntimeServiceException(ErrorCode.SEND_INFO_TO_TERMINAL_FAIL);
        }
        return 0;
    }

    public int sendCmd(byte[] bytes) {

        Channel channel = ChannelMap.getChannelByName(ip_prefix + "1.101");
        channel.writeAndFlush(bytes);

        return 0;
    }


}
