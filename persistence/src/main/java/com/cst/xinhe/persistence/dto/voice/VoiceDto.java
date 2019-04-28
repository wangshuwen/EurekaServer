package com.cst.xinhe.persistence.dto.voice;

/**
 * @program: demo
 * @description: 声音保存发送实体类
 * @author: lifeng
 * @create: 2019-03-05 09:10
 **/
public class VoiceDto {

    private Integer stationId;

    private Integer stationIp1;
    private Integer stationIp2;
    private Integer terminalId;
    private Integer terminalPort;
    private Integer stationPort;
    private Integer terminalIp1;
    private Integer terminalIp2;
    private String terminalIp;
    private String voiceUrl;

    private Integer staffId;

    private Integer userId;

    private Integer seqId;

    public VoiceDto() {
    }

    public VoiceDto(Integer stationId, Integer stationIp1, Integer stationIp2, Integer terminalId, Integer terminalPort, Integer stationPort, Integer terminalIp1, Integer terminalIp2, String terminalIp, String voiceUrl, Integer staffId, Integer userId, Integer seqId) {
        this.stationId = stationId;
        this.stationIp1 = stationIp1;
        this.stationIp2 = stationIp2;
        this.terminalId = terminalId;
        this.terminalPort = terminalPort;
        this.stationPort = stationPort;
        this.terminalIp1 = terminalIp1;
        this.terminalIp2 = terminalIp2;
        this.terminalIp = terminalIp;
        this.voiceUrl = voiceUrl;
        this.staffId = staffId;
        this.userId = userId;
        this.seqId = seqId;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }


    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getStationIp1() {
        return stationIp1;
    }

    public void setStationIp1(Integer stationIp1) {
        this.stationIp1 = stationIp1;
    }

    public Integer getStationIp2() {
        return stationIp2;
    }

    public void setStationIp2(Integer stationIp2) {
        this.stationIp2 = stationIp2;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getTerminalPort() {
        return terminalPort;
    }

    public void setTerminalPort(Integer terminalPort) {
        this.terminalPort = terminalPort;
    }

    public Integer getStationPort() {
        return stationPort;
    }

    public void setStationPort(Integer stationPort) {
        this.stationPort = stationPort;
    }

    public Integer getTerminalIp1() {
        return terminalIp1;
    }

    public void setTerminalIp1(Integer terminalIp1) {
        this.terminalIp1 = terminalIp1;
    }

    public Integer getTerminalIp2() {
        return terminalIp2;
    }

    public void setTerminalIp2(Integer terminalIp2) {
        this.terminalIp2 = terminalIp2;
    }

    @Override
    public String toString() {
        return "VoiceDto{" +
                "stationId=" + stationId +
                ", stationIp1=" + stationIp1 +
                ", stationIp2=" + stationIp2 +
                ", terminalId=" + terminalId +
                ", terminalPort=" + terminalPort +
                ", stationPort=" + stationPort +
                ", terminalIp1=" + terminalIp1 +
                ", terminalIp2=" + terminalIp2 +
                ", terminalIp='" + terminalIp + '\'' +
                ", voiceUrl='" + voiceUrl + '\'' +
                ", staffId=" + staffId +
                ", userId=" + userId +
                ", seqId=" + seqId +
                '}';
    }
}
