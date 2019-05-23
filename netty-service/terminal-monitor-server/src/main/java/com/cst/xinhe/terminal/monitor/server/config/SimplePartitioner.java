package com.cst.xinhe.terminal.monitor.server.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-03-12 16:10
 **/
public class SimplePartitioner implements Partitioner {
    private static final Integer PARTITION_NUM = 6;
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (null == key){
            return 0;
        }
        String keyValue = String.valueOf(key);
        // key取模
        int partitionId = (int) (Long.valueOf(keyValue)%PARTITION_NUM);
        System.out.println("**************************************************************************************");
        System.out.println("**************************************************************************************");
        System.out.println("**************************************************************************************");
        System.out.println("**************************************************************************************");
        System.out.println("**************************************************************************************");
        return partitionId;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
