package com.cst.xinhe.common.utils.array;

/**
 * @program: demo
 * @description: 队列操作接口
 * @author: lifeng
 * @create: 2019-01-25 10:50
 **/
public interface Queue<T> {

    /**
     * @description: 入队
     * @param:
     * @return:
     * @author: lifeng
     * @date: 2019/1/25
     */
    void add(T t);

    /**
     * @description: 出队
     * @param:
     * @return:
     * @author: lifeng
     * @date: 2019/1/25
     */
    void remove();

    /**
     * @description: 判断是否为空
     * @param:
     * @return: 空返回true，非空返回false
     * @author: lifeng
     * @date: 2019/1/25
     */
    boolean isEmpty();

    /**
     * @description: 队列是否已满
     * @param:
     * @return:  队满返回true，否则返回false
     * @author: lifeng
     * @date: 2019/1/25
     */
    boolean isFull();

    /**
     * @description: 获取队长度
     * @param:
     * @return:
     * @author: lifeng
     * @date: 2019/1/25
     */
    int getQueueSize();

    /**
     * @description: 获取队头
     * @param:
     * @return:
     * @author: lifeng
     * @date: 2019/1/25
     */
    T getHead();

    /**
     * @description: 获取队尾
     * @param:
     * @return:
     * @author: lifeng
     * @date: 2019/1/25
     */
    T getEnd();

}
