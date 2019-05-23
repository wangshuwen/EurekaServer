package com.cst.xinhe.chatmessage.service.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-05 10:35
 **/
public interface CallService {

    String callStaffByStaffId(MultipartFile wavFile, Integer staffId, Integer userId);

    boolean pingTerminal(Integer staffId);

    boolean pingTerminalByTerminalNum(Integer terminalNum);
}
