package com.cst.xinhe.common.utils.array;

/**
 * @program: demo
 * @description: 链表队列
 * @author: lifeng
 * @create: 2019-01-25 11:47
 **/
public class ArrayQueue<T> extends AbstractArrayQueue<T> {

    private Integer fixedSize;

    private Integer currentSize;

    private T[] data;

    public ArrayQueue(Integer fixedSize) {
        this.fixedSize = fixedSize;
        data = (T[]) new Object[this.fixedSize];
        this.currentSize = 0;
    }

    @Override
    public void add(T t) {
        if (isFull()){
            for (int i = 0 ; i < currentSize - 1; i++)
                data[i] = data[i+1];
            data[currentSize - 1] = t;
        }else {
            data[currentSize] = t;
            currentSize ++;
        }
    }

    @Override
    public void remove() {
            for (int i = 0; i < currentSize - 1; i++){
                data[i] = data[i + 1];
            data[currentSize - 1] = null;
            currentSize--;
        }
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public boolean isFull() {
        return currentSize == this.fixedSize;
    }

    @Override
    public int getQueueSize() {
        return this.currentSize;
    }

    @Override
    public T getHead() {
        if (isEmpty()){
            try {
                throw new Exception("队列为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return data[0];
        }
        return null;
    }

    @Override
    public T getEnd() {
        if (isEmpty()){
            try {
                throw new Exception("队列为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return data[currentSize - 1];
        }
        return null;
    }

}
