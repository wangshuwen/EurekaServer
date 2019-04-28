package com.cst.xinhe.persistence.dto.chat_msg;

import java.util.Date;

/**
 * @ClassName ChatMsgHistoryDto
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/24 13:08
 * @Vserion v0.0.1
 */

public class ChatMsgHistoryDto {

    private Integer msg_id;
    private String post_msg;
    private Date postTime;

    private String postIp;

    private Integer postUserId;

    private String stationIp;

    private Integer terminalId;

    public Integer getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(Integer msg_id) {
        this.msg_id = msg_id;
    }

    public String getPost_msg() {
        return post_msg;
    }

    public void setPost_msg(String post_msg) {
        this.post_msg = post_msg;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getPostIp() {
        return postIp;
    }

    public void setPostIp(String postIp) {
        this.postIp = postIp;
    }

    public Integer getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(Integer postUserId) {
        this.postUserId = postUserId;
    }

    public String getStationIp() {
        return stationIp;
    }

    public void setStationIp(String stationIp) {
        this.stationIp = stationIp;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }
}
