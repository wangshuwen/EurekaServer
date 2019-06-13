package com.cst.xinhe.persistence.dao.e_call;

import com.cst.xinhe.persistence.model.e_call.ECall;
import com.cst.xinhe.persistence.model.e_call.ECallExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ECallMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    long countByExample(ECallExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    int deleteByExample(ECallExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long eCallId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    int insert(ECall record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    int insertSelective(ECall record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    List<ECall> selectByExample(ECallExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    ECall selectByPrimaryKey(Long eCallId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ECall record, @Param("example") ECallExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ECall record, @Param("example") ECallExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ECall record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table e_call
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ECall record);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);
}