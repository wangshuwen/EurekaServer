package com.cst.xinhe.persistence.dao.lack_electric;

import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.lack_electric.LackElectricExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LackElectricMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    long countByExample(LackElectricExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    int deleteByExample(LackElectricExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer lackElectricId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    int insert(LackElectric record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    int insertSelective(LackElectric record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    List<LackElectric> selectByExample(LackElectricExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    LackElectric selectByPrimaryKey(Integer lackElectricId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LackElectric record, @Param("example") LackElectricExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LackElectric record, @Param("example") LackElectricExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LackElectric record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lack_electric
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LackElectric record);

    List<Map<String,Object>> findLackElectric(@Param("terminalId") Integer terminalId, @Param("staffName") String staffName);

    Integer selectIsReadCount();
}
