package com.cst.xinhe.web.service.server;

import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.web.service.chat.service.ChatMessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 11:34
 **/
@RestController
public class ChatMessageControllerServer {

    @Resource
    private ChatMessageService chatMessageService;

    @PostMapping("insertChatMsgSelective")
    public void insertChatMsgSelective(@RequestBody ChatMsg chatMsg){
        chatMessageService.insertRecord(chatMsg);
    }

}
