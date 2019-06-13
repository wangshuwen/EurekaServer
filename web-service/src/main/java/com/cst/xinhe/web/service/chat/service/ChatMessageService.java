package com.cst.xinhe.web.service.chat.service;

import com.cst.xinhe.persistence.dto.chat_msg.ChatMsgHistoryDto;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public interface ChatMessageService {

    PageInfo<ChatMsgHistoryDto> findMsgHistory(Integer userId, Integer staffId, Integer startPage, Integer pageSize);

    Page findChatList(String keyWord, Integer startPage, Integer pageSize);

    Page findChatRecord(Integer staffId, Integer startPage, Integer pageSize);

    Integer deleteChatRecord(Integer staffId);

    Integer updateMsgStatus(String seqId);

    Integer addChatRecord(ChatMsg chatMsg);

    Integer insertRecord(ChatMsg chatMsg);
}
