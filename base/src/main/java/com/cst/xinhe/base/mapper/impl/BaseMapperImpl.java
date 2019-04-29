package com.cst.xinhe.base.mapper.impl;

import com.cst.xinhe.base.mapper.BaseMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @auther li
 * @date 2018/1/9-15:17
 */

public abstract class BaseMapperImpl<T> extends SqlSessionDaoSupport implements BaseMapper {

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * 命名空间
     */
    private String namespace;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


    public List<T> findByParam(Map paraMap) {
        List<T> list = this.getSqlSession().selectList(namespace + ".findByParam" + paraMap);
        return list;
    }


    public Object selectByPrimaryKey(Serializable id) {
        Object o = this.getSqlSession().selectList(namespace + ".findById" + id);
        return o;
    }

    public List<T> selectAll() {
        List<T> list = this.getSqlSession().selectList(namespace + ".findById");
        return list;
    }

    public int updateByPrimaryKeySelective(Object entity) {
        int result = this.getSqlSession().update(namespace + ".update" + entity);
        return result;
    }


    public Object selectByPrimaryKey(String id) {
        Object o = this.getSqlSession().selectList(namespace + ".findById" + id);
        return o;
    }


    public int insert(Object entity) {
        int result = this.getSqlSession().insert(namespace + ".insert" + entity);
        return result;
    }


    public int deleteByPrimaryKey(String id) {

        int result = this.getSqlSession().delete(namespace + ".deleteById" + id);
        return result;
    }


    public int delete(String[] ids) {
        //TODO
        return Integer.parseInt(null);
    }


    public int updateByPrimaryKey(Object entity) {
        int result = this.getSqlSession().update(namespace + ".update" + entity);
        return result;
    }


    public int insertSelective(Object entity) {
        int result = this.getSqlSession().insert(namespace + ".insert" + entity);
        return result;
    }


    public int deleteByPrimaryKey(Serializable id) {
        int result = this.getSqlSession().delete(namespace + ".deleteById" + id);
        return result;
    }


    public int delete(Serializable[] ids) {

        int result = this.getSqlSession().delete(namespace + ".delete" + ids);
        return result;
    }


}
