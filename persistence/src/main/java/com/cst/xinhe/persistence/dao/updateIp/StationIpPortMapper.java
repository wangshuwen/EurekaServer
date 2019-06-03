package com.cst.xinhe.persistence.dao.updateIp;

import com.cst.xinhe.persistence.model.updateIp.StationIpPort;
import com.cst.xinhe.persistence.model.updateIp.StationIpPortExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StationIpPortMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    long countByExample(StationIpPortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    int deleteByExample(StationIpPortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer stationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    int insert(StationIpPort record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    int insertSelective(StationIpPort record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    List<StationIpPort> selectByExample(StationIpPortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    StationIpPort selectByPrimaryKey(Integer stationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") StationIpPort record, @Param("example") StationIpPortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") StationIpPort record, @Param("example") StationIpPortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(StationIpPort record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table station_ip_port
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StationIpPort record);
}