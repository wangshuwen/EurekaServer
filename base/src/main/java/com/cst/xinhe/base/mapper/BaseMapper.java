package com.cst.xinhe.base.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    /**
     * 根据条件查找
     *
     * @param paraMap
     * @return
     */
    public List<T> findByParam(Map paraMap);

    /**
     * 根据ID查找
     *
     * @param id String
     * @return
     */
    public T selectByPrimaryKey(String id);

    /**
     * 根据ID查找
     *
     * @param id int
     * @return
     */
    public T selectByPrimaryKey(Serializable id);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    public int updateByPrimaryKeySelective(T entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    public int updateByPrimaryKey(T entity);

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public int insertSelective(T entity);

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public int insert(T entity);


    /**
     * 根据ID删除一条数据
     *
     * @param id String
     * @return
     */
    public int deleteByPrimaryKey(String id);

    /**
     * 根据ID删除一条数据
     *
     * @param id int
     * @return
     */
    public int deleteByPrimaryKey(Serializable id);

    /**
     * 根据IDS 批量删除
     *
     * @param ids int
     * @return
     */
    public int delete(Serializable[] ids);

    /**
     * 根据IDS 批量删除
     *
     * @param ids String
     * @return
     */
    public int delete(String[] ids);

    /**
     * 查找全部
     *
     * @return
     */
    public List<T> selectAll();


}
