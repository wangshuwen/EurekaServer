package com.cst.xinhe.persistence.dao.warn_level;

import com.cst.xinhe.persistence.dto.warn_level_setting.GasWarnSettingDto;
import com.cst.xinhe.persistence.model.warn_level.GasWarnSetting;
import com.cst.xinhe.persistence.model.warn_level.GasWarnSettingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GasWarnSettingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    long countByExample(GasWarnSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    int deleteByExample(GasWarnSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer gasWarnSettingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    int insert(GasWarnSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    int insertSelective(GasWarnSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    List<GasWarnSetting> selectByExample(GasWarnSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    GasWarnSetting selectByPrimaryKey(Integer gasWarnSettingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") GasWarnSetting record, @Param("example") GasWarnSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") GasWarnSetting record, @Param("example") GasWarnSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GasWarnSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gas_warn_setting
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(GasWarnSetting record);

    List<GasWarnSettingDto> selectGasWarnSettingByParams(Map<String, Object> params);
}
