package com.cst.xinhe.persistence.dao.warning_area;

import com.cst.xinhe.persistence.dto.warning_area.CoordinateDto;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarningAreaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    long countByExample(WarningAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    int deleteByExample(WarningAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer warningAreaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    int insert(WarningArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    int insertSelective(WarningArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    List<WarningArea> selectByExample(WarningAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    WarningArea selectByPrimaryKey(Integer warningAreaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") WarningArea record, @Param("example") WarningAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") WarningArea record, @Param("example") WarningAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WarningArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_area
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WarningArea record);

    CoordinateDto getAreaInfoByAreaId(Integer areaId);
    List<CoordinateDto> getOtherAreaInfoByAreaId(Integer areaId);

    List<CoordinateDto> getAll();
}