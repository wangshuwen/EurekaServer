package com.cst.xinhe.persistence.model.chat;


import com.cst.xinhe.base.entity.BaseEntity;

import java.util.Date;

public class ChatMsg extends BaseEntity {

    private Integer msgId;

    private String postMsg;


    private Integer lengthMsg;


    private Boolean status;


    private Date postTime;


    private Integer postUserId;


    private String postIp;


    private String receiveIp;

    private Integer receiceUserId;


    private Boolean turnSend;


    private String stationIp;


    private Date convertTime;


    private Integer terminalId;

    //后补
    private Integer isDel;

    private String sequenceId;

    private Integer type;

    private String timeLong;

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Integer getMsgId() {
        return msgId;
    }


    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }


    public String getPostMsg() {
        return postMsg;
    }


    public void setPostMsg(String postMsg) {
        this.postMsg = postMsg == null ? null : postMsg.trim();
    }


    public Integer getLengthMsg() {
        return lengthMsg;
    }


    public void setLengthMsg(Integer lengthMsg) {
        this.lengthMsg = lengthMsg;
    }


    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }


    public Date getPostTime() {
        return postTime;
    }


    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }


    public Integer getPostUserId() {
        return postUserId;
    }


    public void setPostUserId(Integer postUserId) {
        this.postUserId = postUserId;
    }


    public String getPostIp() {
        return postIp;
    }


    public void setPostIp(String postIp) {
        this.postIp = postIp == null ? null : postIp.trim();
    }


    public String getReceiveIp() {
        return receiveIp;
    }


    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp == null ? null : receiveIp.trim();
    }


    public Integer getReceiceUserId() {
        return receiceUserId;
    }


    public void setReceiceUserId(Integer receiceUserId) {
        this.receiceUserId = receiceUserId;
    }


    public Boolean getTurnSend() {
        return turnSend;
    }


    public void setTurnSend(Boolean turnSend) {
        this.turnSend = turnSend;
    }


    public String getStationIp() {
        return stationIp;
    }


    public void setStationIp(String stationIp) {
        this.stationIp = stationIp == null ? null : stationIp.trim();
    }


    public Date getConvertTime() {
        return convertTime;
    }

    public void setConvertTime(Date convertTime) {
        this.convertTime = convertTime;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "msgId=" + msgId +
                ", postMsg='" + postMsg + '\'' +
                ", lengthMsg=" + lengthMsg +
                ", status=" + status +
                ", postTime=" + postTime +
                ", postUserId=" + postUserId +
                ", postIp='" + postIp + '\'' +
                ", receiveIp='" + receiveIp + '\'' +
                ", receiceUserId=" + receiceUserId +
                ", turnSend=" + turnSend +
                ", stationIp='" + stationIp + '\'' +
                ", convertTime=" + convertTime +
                ", terminalId=" + terminalId +
                ", isDel=" + isDel +
                ", sequenceId='" + sequenceId + '\'' +
                '}';
    }
}
