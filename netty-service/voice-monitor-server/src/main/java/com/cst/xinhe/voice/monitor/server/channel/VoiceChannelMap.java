package com.cst.xinhe.voice.monitor.server.channel;


import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ChannelMap
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/28 16:31
 * @Vserion v0.0.1
 */

public class VoiceChannelMap {


    public static int channelNum = 0;

    /**
     * @param []
     * @return int
     * @description 获取 连接终端个数
     * @date 11:33 2018/11/22
     * @auther lifeng
     **/
    public static synchronized int getChannelNum() {
        return channelNum;
    }

    /**
     * @param []
     * @return void
     * @description 新加入连接终端 加入Map
     * @date 11:33 2018/11/22
     * @auther lifeng
     **/
    public static synchronized void addChannelNum() {
        VoiceChannelMap.channelNum++;
    }

    /**
     * @param []
     * @return void
     * @description
     * @date 11:34 2018/11/22
     * @auther lifeng
     **/
    public static synchronized void removeChannelNum() {
        VoiceChannelMap.channelNum--;
    }


    //定义线程安全的 Map集合
    private static ConcurrentHashMap<String, Channel> channelHashMap = null;

    /**
     * @param [name]
     * @return io.netty.channel.Channel
     * @description 通过 Name（连入的IP）
     * @date 11:36 2018/11/22
     * @auther lifeng
     **/
    public static Channel getChannelByName(String name) {
        return channelHashMap.get(name);
    }

    public static void addChannel(String name, Channel channel) {
        if (channelHashMap == null) {
            channelHashMap = new ConcurrentHashMap<>(100);
        }
        channelHashMap.put(name, channel);
        channelNum++;
    }

    public static int removeChannelByName(String name) {
        if (channelHashMap.containsKey(name)) {
            channelHashMap.remove(name);
            channelNum--;
            return 0;
        } else {
            return 1;
        }
    }

}
