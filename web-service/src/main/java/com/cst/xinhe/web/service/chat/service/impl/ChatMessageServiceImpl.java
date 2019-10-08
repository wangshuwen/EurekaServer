package com.cst.xinhe.web.service.chat.service.impl;


import com.cst.xinhe.persistence.dao.chat.ChatMsgMapper;
import com.cst.xinhe.persistence.dto.chat_msg.ChatMsgHistoryDto;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.web.service.chat.service.ChatMessageService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffOrganizationService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffTerminalRelationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-05 10:06
 **/
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Resource
    private ChatMsgMapper chatMsgMapper;
    @Resource
    private StaffOrganizationService staffOrganizationService;


    @Override
    public PageInfo<ChatMsgHistoryDto> findMsgHistory(Integer userId, Integer staffId, Integer startPage, Integer pageSize) {

        PageHelper.startPage(startPage, pageSize);
        List<Map<String, Object>> map = chatMsgMapper.selectMsgByUserIdAndStaffId(userId, staffId);
        List<ChatMsgHistoryDto> list = Collections.synchronizedList(new ArrayList<>());
        ChatMsgHistoryDto chatMsgHistoryDto = null;
        for (Map<String, Object> item : map) {
            Integer msgId = (Integer) item.get("msg_id");
            String postMsg = (String) item.get("post_msg");
            String postIp = (String) item.get("post_ip");

            Date post_time = (Date) item.get("post_time");

            Integer post_user_id = (Integer) item.get("post_user_id");

            String station_ip = (String) item.get("station_ip");

            Integer terminal_id = (Integer) item.get("terminal_id");

            chatMsgHistoryDto = new ChatMsgHistoryDto();
            chatMsgHistoryDto.setMsg_id(msgId);
            chatMsgHistoryDto.setPost_msg(postMsg);
            chatMsgHistoryDto.setPostIp(postIp);
            chatMsgHistoryDto.setPostTime(post_time);
            chatMsgHistoryDto.setPostUserId(post_user_id);
            chatMsgHistoryDto.setStationIp(station_ip);
            chatMsgHistoryDto.setTerminalId(terminal_id);
            list.add(chatMsgHistoryDto);
        }

        PageInfo<ChatMsgHistoryDto> l = new PageInfo<>(list);
        return l;
    }

    @Override
    public Page findChatList(String keyWord, Integer startPage, Integer pageSize) {
        //通过部门名称，查询该部门下的所有子节点，返回部门ids
        List<Integer> deptIds=null;
        if(keyWord!=null&&!"".equals(keyWord)){
//            deptIds = staffOrganizationService.findSonIdsByDeptName(keyWord);
            deptIds = staffOrganizationService.findSonIdsByDeptName(keyWord);
        }
        Page page = PageHelper.startPage(startPage, pageSize);
        List<Map<String, Object>> list1 = chatMsgMapper.findChatList(keyWord, deptIds);
        List<Map<String,Object>> list = page.getResult();
        if(null != list &&list.size()>0){
            //封装groupId，查找groupName
            List<Integer> groupIds = new ArrayList<>();
            for (Map<String, Object> map : list) {
                Integer groupId = (Integer) map.get("groupId");
                groupIds.add(groupId);
            }
            //服务调用
            List<Map<String, Object>> groupInfo = staffOrganizationService.getDeptNameByGroupIds(groupIds);


            for (Map<String, Object> map : list) {
                Integer groupId = (Integer) map.get("groupId");
                Integer staffId = (Integer) map.get("staffId");
//                String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
                for (Map<String, Object> group : groupInfo) {
                    Integer group_Id= (Integer) group.get("groupId");
                    if(group_Id!=null){
                        if(group_Id.equals(groupId)){
                            String groupName= (String) group.get("groupName");
                            map.put("deptName",groupName);
                        }
                    }
                }


                //查找未读的消息数目，用于显示在聊天列表上未读的消息个数
                Integer count=chatMsgMapper.findUnReadCount(staffId);

                if(count!=null){
                    map.put("count",count);
                }else{
                    map.put("count",0);
                }
            }
        }

        return page;
    }

    @Override
    public Page findChatRecord(Integer staffId, Integer startPage, Integer pageSize) {
        Page page = PageHelper.startPage(startPage, pageSize);
        List<Map<String, Object>> lists = chatMsgMapper.findChatRecord(staffId);
        return page;
    }

    @Override
    public Integer deleteChatRecord(Integer staffId) {
        return chatMsgMapper.deleteChatRecord(staffId);
    }

    @Override
    public Integer updateMsgStatus(String seqId) {
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setSequenceId(seqId);
//        chatMsg.setStatus(true);
        return chatMsgMapper.updateChatMegStatusBySeqId(chatMsg);
    }

    @Override
    public Integer addChatRecord(ChatMsg chatMsg) {
        chatMsg.setReceiceUserId(0);
        List<Map<String, Object>> list = chatMsgMapper.selectMsgByUserIdAndStaffId(0, chatMsg.getPostUserId());

        if(list==null||list.size()==0){
            return chatMsgMapper.insertSelective(chatMsg);
        }else{
            Map<String, Object> map = list.get(0);
            Integer msgId = (Integer) map.get("msgId");
            ChatMsg chatMsg1 = new ChatMsg();
            chatMsg1.setIsDel(0);
            chatMsg1.setMsgId(msgId);
            chatMsg1.setPostTime(chatMsg.getPostTime());
            chatMsgMapper.updateByPrimaryKeySelective(chatMsg1);
        }
        return 1;
    }

    @Override
    public Integer insertRecord(ChatMsg chatMsg) {
        return chatMsgMapper.insertSelective(chatMsg);
    }

    @Override
    public Integer getSingleVoiceNum(Integer staffId) {

     Integer count= chatMsgMapper.getSingleVoiceNum(staffId);
        return count;
    }
}
