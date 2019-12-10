package com.cst.xinhe.persistence.dao.overman_staff;


import com.cst.xinhe.persistence.model.overman_staff.OvermanStaff;
import com.cst.xinhe.persistence.model.overman_staff.OvermanStaffExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OvermanStaffMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    long countByExample(OvermanStaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    int deleteByExample(OvermanStaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer overmanStaffId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    int insert(OvermanStaff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    int insertSelective(OvermanStaff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    List<OvermanStaff> selectByExample(OvermanStaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    OvermanStaff selectByPrimaryKey(Integer overmanStaffId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") OvermanStaff record, @Param("example") OvermanStaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") OvermanStaff record, @Param("example") OvermanStaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(OvermanStaff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table overman_staff
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OvermanStaff record);
}