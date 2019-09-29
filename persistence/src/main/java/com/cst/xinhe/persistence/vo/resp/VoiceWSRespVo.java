package com.cst.xinhe.persistence.vo.resp;

import java.util.Date;

/**
 * @ClassName VoiceWSRespVo
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/17 16:55
 * @Vserion v0.0.1
 */

public class VoiceWSRespVo {

    private Integer staffId;

    private Integer terminalId;

    private String postMsg;

    private boolean status;

    private Date uploadTime;

    private String deptName;

    private String staffName;

    private String sequenceId;

    private GasWSRespVO gasWSRespVO;
    private String rangUrl;

    public String getRangUrl() {
        return rangUrl;
    }

    public void setRangUrl(String rangUrl) {
        this.rangUrl = rangUrl;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public String getPostMsg() {
        return postMsg;
    }

    public void setPostMsg(String postMsg) {
        this.postMsg = postMsg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public GasWSRespVO getGasWSRespVO() {
        return gasWSRespVO;
    }

    public void setGasWSRespVO(GasWSRespVO gasWSRespVO) {
        this.gasWSRespVO = gasWSRespVO;
    }
}
