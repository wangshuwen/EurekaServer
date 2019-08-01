package com.cst.xinhe.persistence.dto.voice;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-07-18 14:09
 **/
public class UploadAudioDto {
    private MultipartFile waveFile;

    private Integer staffId;

    public MultipartFile getWaveFile() {
        return waveFile;
    }

    public void setWaveFile(MultipartFile waveFile) {
        this.waveFile = waveFile;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}
