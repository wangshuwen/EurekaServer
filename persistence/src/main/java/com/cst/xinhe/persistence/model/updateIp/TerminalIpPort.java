package com.cst.xinhe.persistence.model.updateIp;

import java.util.Date;

public class TerminalIpPort {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column terminal_ip_port.terminal_id
     *
     * @mbg.generated
     */
    private Integer terminalId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column terminal_ip_port.terminal_port
     *
     * @mbg.generated
     */
    private Integer terminalPort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column terminal_ip_port.terminal_ip
     *
     * @mbg.generated
     */
    private String terminalIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column terminal_ip_port.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column terminal_ip_port.terminal_id
     *
     * @return the value of terminal_ip_port.terminal_id
     *
     * @mbg.generated
     */
    public Integer getTerminalId() {
        return terminalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column terminal_ip_port.terminal_id
     *
     * @param terminalId the value for terminal_ip_port.terminal_id
     *
     * @mbg.generated
     */
    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column terminal_ip_port.terminal_port
     *
     * @return the value of terminal_ip_port.terminal_port
     *
     * @mbg.generated
     */
    public Integer getTerminalPort() {
        return terminalPort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column terminal_ip_port.terminal_port
     *
     * @param terminalPort the value for terminal_ip_port.terminal_port
     *
     * @mbg.generated
     */
    public void setTerminalPort(Integer terminalPort) {
        this.terminalPort = terminalPort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column terminal_ip_port.terminal_ip
     *
     * @return the value of terminal_ip_port.terminal_ip
     *
     * @mbg.generated
     */
    public String getTerminalIp() {
        return terminalIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column terminal_ip_port.terminal_ip
     *
     * @param terminalIp the value for terminal_ip_port.terminal_ip
     *
     * @mbg.generated
     */
    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp == null ? null : terminalIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column terminal_ip_port.update_time
     *
     * @return the value of terminal_ip_port.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column terminal_ip_port.update_time
     *
     * @param updateTime the value for terminal_ip_port.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}