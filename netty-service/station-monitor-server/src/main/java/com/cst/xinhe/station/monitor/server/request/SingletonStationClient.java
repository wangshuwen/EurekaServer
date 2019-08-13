package com.cst.xinhe.station.monitor.server.request;

import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.station.monitor.server.channel.ChannelMap;
import io.netty.channel.Channel;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: demo
 * @description: 单例基站的响应类
 * @author: lifeng
 * @create: 2019-01-15 15:33
 **/
public class SingletonStationClient {

    //从配置文件获取ip前缀
    private String ip_prefix;

    private volatile static SingletonStationClient client;

    private SingletonStationClient(){
        this.ip_prefix = "192.168.";
    }

    public static SingletonStationClient getSingletonStationClient() {
        if (client == null) {
            synchronized (SingletonStationClient.class) {
                if (client == null) {
                    client = new SingletonStationClient();
                }
            }
        }
        return client;
    }
    /**
     * @param [responseData]
     * @return int
     * @description 模拟客户端，netty server 向客户端发送返回的数据
     * 通过参数 获取到 连接中的key ，从ChannelMap中获取 Channel
     * 向Channel中回写数据
     * @date 14:47 2018/11/26
     * @auther lifeng
     **/
    public int sendCmd(ResponseData responseData) {

        String t_ip = responseData.getCustomMsg().getStationIp();

        StringBuffer stringBuffer = new StringBuffer(ip_prefix);
        stringBuffer.append(t_ip).append(":").append(responseData.getCustomMsg().getStationPort());
        String ip = stringBuffer.toString();
        System.out.println("ChannelMap:"+ip);
        Channel channel = ChannelMap.getChannelByName(ip);
        channel.writeAndFlush(responseData);

        return 0;
    }

    public int sendCmd(byte[] bytes) {

        StringBuffer stringBuffer = new StringBuffer(ip_prefix);
        stringBuffer.append("1.101");
        Channel channel = ChannelMap.getChannelByName(stringBuffer.toString());
        channel.writeAndFlush(bytes);

        return 0;
    }


    public int sendToAllClientCmd(ResponseData responseData) {

        AtomicInteger count = new AtomicInteger();
        ChannelMap.channelHashMap.forEach((k,v)-> {v.writeAndFlush(responseData);
        count.incrementAndGet();});

        return count.getAndDecrement();
    }


}
