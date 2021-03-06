package com.cst.xinhe.persistence.dao.staff;

import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff.StaffJobExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffJobMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    long countByExample(StaffJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    int deleteByExample(StaffJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer jobId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    int insert(StaffJob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    int insertSelective(StaffJob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    List<StaffJob> selectByExample(StaffJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    StaffJob selectByPrimaryKey(Integer jobId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") StaffJob record, @Param("example") StaffJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") StaffJob record, @Param("example") StaffJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(StaffJob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff_job
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StaffJob record);

    List<StaffJob> selectAllJobs();

    List<StaffJob> selectJobByJobName(@Param("jobName") String jobName);
}
