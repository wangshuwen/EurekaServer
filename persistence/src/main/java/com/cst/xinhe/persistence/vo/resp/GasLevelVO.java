package com.cst.xinhe.persistence.vo.resp;


import com.cst.xinhe.persistence.dto.warn_level_setting.GasWarnSettingDto;
import com.cst.xinhe.persistence.model.warn_level.GasStandard;

import java.util.List;

/**
 * @program: demo
 * @description: 气体标准信息响应VO类
 * @author: lifeng
 * @create: 2019-01-19 10:16
 **/
public class GasLevelVO {

    private GasStandard gasStandard;
    List<GasWarnSettingDto> o2WarnSettingDto;
    List<GasWarnSettingDto> ch4WarnSettingDto;
    List<GasWarnSettingDto> coWarnSettingDto;
    List<GasWarnSettingDto> tWarnSettingDto;
    List<GasWarnSettingDto> hWarnSettingDto;

    public GasLevelVO() {
    }

    public GasLevelVO(GasStandard gasStandard, List<GasWarnSettingDto> o2WarnSettingDto, List<GasWarnSettingDto> ch4WarnSettingDto, List<GasWarnSettingDto> coWarnSettingDto, List<GasWarnSettingDto> tWarnSettingDto, List<GasWarnSettingDto> hWarnSettingDto) {
        this.gasStandard = gasStandard;
        this.o2WarnSettingDto = o2WarnSettingDto;
        this.ch4WarnSettingDto = ch4WarnSettingDto;
        this.coWarnSettingDto = coWarnSettingDto;
        this.tWarnSettingDto = tWarnSettingDto;
        this.hWarnSettingDto = hWarnSettingDto;
    }

    public GasStandard getGasStandard() {
        return gasStandard;
    }

    public void setGasStandard(GasStandard gasStandard) {
        this.gasStandard = gasStandard;
    }

    public List<GasWarnSettingDto> getO2WarnSettingDto() {
        return o2WarnSettingDto;
    }

    public void setO2WarnSettingDto(List<GasWarnSettingDto> o2WarnSettingDto) {
        this.o2WarnSettingDto = o2WarnSettingDto;
    }

    public List<GasWarnSettingDto> getCh4WarnSettingDto() {
        return ch4WarnSettingDto;
    }

    public void setCh4WarnSettingDto(List<GasWarnSettingDto> ch4WarnSettingDto) {
        this.ch4WarnSettingDto = ch4WarnSettingDto;
    }

    public List<GasWarnSettingDto> getCoWarnSettingDto() {
        return coWarnSettingDto;
    }

    public void setCoWarnSettingDto(List<GasWarnSettingDto> coWarnSettingDto) {
        this.coWarnSettingDto = coWarnSettingDto;
    }

    public List<GasWarnSettingDto> gettWarnSettingDto() {
        return tWarnSettingDto;
    }

    public void settWarnSettingDto(List<GasWarnSettingDto> tWarnSettingDto) {
        this.tWarnSettingDto = tWarnSettingDto;
    }

    public List<GasWarnSettingDto> gethWarnSettingDto() {
        return hWarnSettingDto;
    }

    public void sethWarnSettingDto(List<GasWarnSettingDto> hWarnSettingDto) {
        this.hWarnSettingDto = hWarnSettingDto;
    }

    @Override
    public String toString() {
        return "GasLevelVO{" +
                "gasStandard=" + gasStandard +
                ", o2WarnSettingDto=" + o2WarnSettingDto +
                ", ch4WarnSettingDto=" + ch4WarnSettingDto +
                ", coWarnSettingDto=" + coWarnSettingDto +
                ", tWarnSettingDto=" + tWarnSettingDto +
                ", hWarnSettingDto=" + hWarnSettingDto +
                '}';
    }
}
