package com.cst.xinhe.persistence.dao.staff;

import com.cst.xinhe.persistence.dto.staff.StaffDto;
import com.cst.xinhe.persistence.dto.staff.StaffInfoDto;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffExample;
import com.cst.xinhe.persistence.vo.req.StaffInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StaffMapper {

    List<Integer> selectStaffIdByName(String staffName);

    String selectStaffNameById(Integer staffId);

    String selectStaffNumberById(Integer staffId);

    List<StaffDto> selectStaffsByGroupId(Integer groupId);

    List<StaffInfoDto> selectStaffByParams(@Param("staffName") String staffName, @Param("orgList") List<Integer> orgList,  @Param("isPerson") Integer isPerson,@Param("staffJobId") Integer staffJobId);

    Map<String, Object> selectGroupAndDeptByStaffId(Integer staffId);

    Map<String, Object> selectStaffInfoByTerminalId(Integer terminalId);

    HashMap<String,Object> getDeptAndGroupNameByStaffId(Integer staffId);

    List<Map<String, Object>> selectStaffInfoByParamsOfMap(Map<String, Object> params);

    List<Integer> selectStaffsByDeptId(Integer deptId);

    List<Integer> selectStaffIdsByGroupId(Integer groupId);

    List<Map<String, Object>> selectStaffAttendanceRules();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    long countByExample(StaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    int deleteByExample(StaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer staffId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    int insert(Staff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    int insertSelective(Staff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    List<Staff> selectByExample(StaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    Staff selectByPrimaryKey(Integer staffId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Staff record, @Param("example") StaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Staff record, @Param("example") StaffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Staff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table staff
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Staff record);

    int insertStaffs(@Param("list") List<StaffInfoVO> staffInfoVOs);

    List<Integer> findStaffIdByStaffType(@Param("staffType") Integer staffType);

    String findStaffTypeById(@Param("staffId") Integer staffId);
}
