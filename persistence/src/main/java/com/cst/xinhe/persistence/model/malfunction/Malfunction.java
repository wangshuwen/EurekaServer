package com.cst.xinhe.persistence.model.malfunction;

import java.io.Serializable;
import java.util.Date;

public class Malfunction implements Serializable,Cloneable {


    private static final long serialVersionUID = 6806234947105465378L;
    private static Malfunction malfunction = new Malfunction();

    public static Malfunction getInstance(){
        try {
            return (Malfunction) malfunction.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new Malfunction();
    }

    //后补：用于封装对象到页面显示
    private String deptName;
    private String groupName;
    public String staffName;

    public Integer electric;

    public Integer getElectric() {
        return electric;
    }

    public void setElectric(Integer electric) {
        this.electric = electric;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.self_check_id
     *
     * @mbg.generated
     */
    private Integer selfCheckId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.self_check_time
     *
     * @mbg.generated
     */
    private Date selfCheckTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.terminal_id
     *
     * @mbg.generated
     */
    private Integer terminalId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.terminal_ip
     *
     * @mbg.generated
     */
    private String terminalIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.wifi_error
     *
     * @mbg.generated
     */
    private Integer wifiError;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.voice_error
     *
     * @mbg.generated
     */
    private Integer voiceError;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.co_error
     *
     * @mbg.generated
     */
    private Integer coError;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.co2_error
     *
     * @mbg.generated
     */
    private Integer co2Error;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.ch4_error
     *
     * @mbg.generated
     */
    private Integer ch4Error;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.t_error
     *
     * @mbg.generated
     */
    private Integer tError;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.h_error
     *
     * @mbg.generated
     */
    private Integer hError;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column malfunction.o2_error
     *
     * @mbg.generated
     */
    private Integer o2Error;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.self_check_id
     *
     * @return the value of malfunction.self_check_id
     *
     * @mbg.generated
     */
    public Integer getSelfCheckId() {
        return selfCheckId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.self_check_id
     *
     * @param selfCheckId the value for malfunction.self_check_id
     *
     * @mbg.generated
     */
    public void setSelfCheckId(Integer selfCheckId) {
        this.selfCheckId = selfCheckId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.self_check_time
     *
     * @return the value of malfunction.self_check_time
     *
     * @mbg.generated
     */
    public Date getSelfCheckTime() {
        return selfCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.self_check_time
     *
     * @param selfCheckTime the value for malfunction.self_check_time
     *
     * @mbg.generated
     */
    public void setSelfCheckTime(Date selfCheckTime) {
        this.selfCheckTime = selfCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.terminal_id
     *
     * @return the value of malfunction.terminal_id
     *
     * @mbg.generated
     */
    public Integer getTerminalId() {
        return terminalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.terminal_id
     *
     * @param terminalId the value for malfunction.terminal_id
     *
     * @mbg.generated
     */
    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.terminal_ip
     *
     * @return the value of malfunction.terminal_ip
     *
     * @mbg.generated
     */
    public String getTerminalIp() {
        return terminalIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.terminal_ip
     *
     * @param terminalIp the value for malfunction.terminal_ip
     *
     * @mbg.generated
     */
    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp == null ? null : terminalIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.wifi_error
     *
     * @return the value of malfunction.wifi_error
     *
     * @mbg.generated
     */
    public Integer getWifiError() {
        return wifiError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.wifi_error
     *
     * @param wifiError the value for malfunction.wifi_error
     *
     * @mbg.generated
     */
    public void setWifiError(Integer wifiError) {
        this.wifiError = wifiError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.voice_error
     *
     * @return the value of malfunction.voice_error
     *
     * @mbg.generated
     */
    public Integer getVoiceError() {
        return voiceError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.voice_error
     *
     * @param voiceError the value for malfunction.voice_error
     *
     * @mbg.generated
     */
    public void setVoiceError(Integer voiceError) {
        this.voiceError = voiceError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.co_error
     *
     * @return the value of malfunction.co_error
     *
     * @mbg.generated
     */
    public Integer getCoError() {
        return coError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.co_error
     *
     * @param coError the value for malfunction.co_error
     *
     * @mbg.generated
     */
    public void setCoError(Integer coError) {
        this.coError = coError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.co2_error
     *
     * @return the value of malfunction.co2_error
     *
     * @mbg.generated
     */
    public Integer getCo2Error() {
        return co2Error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.co2_error
     *
     * @param co2Error the value for malfunction.co2_error
     *
     * @mbg.generated
     */
    public void setCo2Error(Integer co2Error) {
        this.co2Error = co2Error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.ch4_error
     *
     * @return the value of malfunction.ch4_error
     *
     * @mbg.generated
     */
    public Integer getCh4Error() {
        return ch4Error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.ch4_error
     *
     * @param ch4Error the value for malfunction.ch4_error
     *
     * @mbg.generated
     */
    public void setCh4Error(Integer ch4Error) {
        this.ch4Error = ch4Error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.t_error
     *
     * @return the value of malfunction.t_error
     *
     * @mbg.generated
     */
    public Integer gettError() {
        return tError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.t_error
     *
     * @param tError the value for malfunction.t_error
     *
     * @mbg.generated
     */
    public void settError(Integer tError) {
        this.tError = tError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.h_error
     *
     * @return the value of malfunction.h_error
     *
     * @mbg.generated
     */
    public Integer gethError() {
        return hError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.h_error
     *
     * @param hError the value for malfunction.h_error
     *
     * @mbg.generated
     */
    public void sethError(Integer hError) {
        this.hError = hError;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.create_time
     *
     * @return the value of malfunction.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.create_time
     *
     * @param createTime the value for malfunction.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.status
     *
     * @return the value of malfunction.status
     *
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.status
     *
     * @param status the value for malfunction.status
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column malfunction.o2_error
     *
     * @return the value of malfunction.o2_error
     *
     * @mbg.generated
     */
    public Integer getO2Error() {
        return o2Error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column malfunction.o2_error
     *
     * @param o2Error the value for malfunction.o2_error
     *
     * @mbg.generated
     */
    public void setO2Error(Integer o2Error) {
        this.o2Error = o2Error;
    }
}