package com.cst.xinhe.chatmessage.service.service;

import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-05 10:35
 **/
public interface CallService {

    ChatMsg callStaffByStaffId(MultipartFile wavFile, Integer staffId, Integer userId);

    boolean pingTerminal(Integer staffId);

    boolean pingTerminalByTerminalNum(Integer terminalNum);

    PageInfo<Map<String, Object>> getECallList(Integer pageSize, Integer startPage, String staffName, Integer staffId);
}
