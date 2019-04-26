package com.cst.xinhe.station.monitor.server.channel;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-26 14:37
 **/
public class ChannelMap {
    public static int channelNum = 0;

    public static synchronized int getChannelNum() {
        return channelNum;
    }

    public static synchronized void addChannelNum() {
        ChannelMap.channelNum++;
    }

    public static synchronized void removeChannelNum() {
        ChannelMap.channelNum--;
    }


    private static ConcurrentHashMap<String, Channel> channelHashMap = new ConcurrentHashMap<>();

    public static Channel getChannelByName(String name) {
        return channelHashMap.get(name);
    }

    public static boolean channelMapContainsKey(String name){
        return channelHashMap.containsKey(name);
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
