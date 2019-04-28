package com.cst.xinhe.persistence.dto;


import java.io.Serializable;

/**
 * @ClassName UpLoadGasDto
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/12 11:07
 * @Vserion v0.0.1
 */

public class UpLoadGasDto implements Serializable,Cloneable {


    private static final long serialVersionUID = -7891176495761822590L;
    private static UpLoadGasDto upLoadGasDto = new UpLoadGasDto();

    public static UpLoadGasDto getInstance(){
        try {
            return (UpLoadGasDto) upLoadGasDto.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new UpLoadGasDto();
    }
    private Integer terminalId;
    private Integer stationId;
    private String terminalIp;
    private String stationIp;
    private Integer sequenceId;
    private String RT;
    private String createTime;
    private String result;
    private GasInfo gasInfo;

    private RssiInfo rssiInfo;

    public UpLoadGasDto() {
    }

    public RssiInfo getRssiInfo() {
        return rssiInfo;
    }

    public void setRssiInfo(RssiInfo rssiInfo) {
        this.rssiInfo = rssiInfo;
    }

    public GasInfo getGasInfo() {
        return gasInfo;
    }

    public void setGasInfo(GasInfo gasInfo) {
        this.gasInfo = gasInfo;
    }


    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public String getStationIp() {
        return stationIp;
    }

    public void setStationIp(String stationIp) {
        this.stationIp = stationIp;
    }

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getRT() {
        return RT;
    }

    public void setRT(String RT) {
        this.RT = RT;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }




}
