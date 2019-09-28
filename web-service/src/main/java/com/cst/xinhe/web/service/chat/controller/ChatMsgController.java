package com.cst.xinhe.web.service.chat.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;

import com.cst.xinhe.common.constant.ConstantUrl;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.dto.chat_msg.ChatMsgHistoryDto;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.web.service.chat.service.ChatMessageService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;


import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ChatMsgController
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/24 11:30
 * @Vserion v0.0.1
 */
@RestController
public class ChatMsgController {

    @Resource
    private ChatMessageService chatMsgService;

   /* @Autowired
    private Constant constant;*/


     @Resource
    private StaffTerminalMapper staffTerminalMapper;



    @GetMapping ("call/newCallRecord")
    @ApiOperation(value = "新增实时语音通话记录", notes = ".0")
    public String newCallRecord(@RequestParam(name = "postUserId") Integer postUserId,
                                @RequestParam(name = "receiceUserId") Integer receiceUserId,
                                @RequestParam(name = "timeLong",required = false)  String  timeLong,@RequestParam(name = "type") Integer type) {
        Integer terminalId;
        if(postUserId!=null&&postUserId!=0){
            terminalId = staffTerminalMapper.selectTerminalIdByStaffId(postUserId);
        }else{
            terminalId = staffTerminalMapper.selectTerminalIdByStaffId(receiceUserId);
        }
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setPostUserId(postUserId);
        chatMsg.setReceiceUserId(receiceUserId);
        chatMsg.setPostMsg(timeLong);
        chatMsg.setPostTime(new Date());
        chatMsg.setLengthMsg(type);
        if(terminalId!=null){
            chatMsg.setTerminalId(terminalId);
        }

        Integer result = chatMsgService.insertRecord(chatMsg);


        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }



    /**
     * @param
     * @return
     * @description 获取历史信息
     * @date 11:03 2018/10/24
     * @auther lifeng
     **/
    @GetMapping("call/chatMsgHistory")
    @ApiOperation(value = "获取聊天分页数据", notes = "根据系统用户ID，员工ID查询历史聊天记录")
    public String getHistoryChatMsg(Integer userId, Integer staffId,
                                    @RequestParam(name = "startPage", defaultValue = "1", required = false) Integer startPage,
                                    @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        PageInfo<ChatMsgHistoryDto> pageInfo = chatMsgService.findMsgHistory(userId, staffId, startPage, pageSize);
        return ResultUtil.jsonToStringSuccess(pageInfo);
    }




    @GetMapping("call/{staffId}")
    @ApiOperation(value = "对员工呼叫实现实时语音", notes = "根据系统用户ID，员工ID查询历史聊天记录")
    public String realTimeCall(@PathVariable(name = "staffId") Integer staffId) {

        return ResultUtil.jsonToStringSuccess();
    }

    @GetMapping("call/chatList")
    @ApiOperation(value = "获取聊天列表", notes = "查询聊天记录列表")
    public String chatList(@RequestParam(name ="keyWord" ,required = false) String keyWord,
                           @RequestParam(name = "startPage", defaultValue = "1", required = false) Integer startPage,
                           @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        Page page= chatMsgService.findChatList(keyWord,startPage,pageSize);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo):ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    long tempTime=0;

    @GetMapping("call/chatRecord")
    @ApiOperation(value = "获取聊天记录", notes = "根据员工id，分页查询聊天记录")
    public String chatRecord( @RequestParam(name = "staffId") Integer staffId,
                             @RequestParam(name = "startPage", defaultValue = "1", required = false) Integer startPage,
                             @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        Page page = chatMsgService.findChatRecord(staffId,startPage,pageSize);
        List<HashMap<String,Object>> result = page.getResult();
        Collections.reverse(result);

        for (HashMap<String, Object> map : result) {
            Date postTime = (Date) map.get("postTime");

            //两条消息记录时间大于10分钟，不显示时间
            long nowTime = postTime.getTime();
            if(nowTime-tempTime<10*60*1000){
                map.put("isShow",false);
            }else{
                map.put("isShow",true);
            }
            tempTime=nowTime;


            String postMsg = (String) map.get("postMsg");
            if(null != postMsg){
                postMsg = postMsg.replace(ConstantUrl.basePath,ConstantUrl.webBaseUrl);
                map.put("postMsg",postMsg);
            }
        }

        tempTime=0;

        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ?ResultUtil.jsonToStringSuccess(pageInfo):ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @DeleteMapping("call/deleteChatRecord")
    @ApiOperation(value = "删除聊天记录", notes = "根据员工id，删除员工聊天记录")
    public String deleteChatRecord(Integer staffId) {
            Integer result=chatMsgService.deleteChatRecord(staffId);

        return result >0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }


    private Date date=new Date();
    @PostMapping("call/addChatRecord")
    @ApiOperation(value = "新增聊天列表，无聊天记录", notes = ".0")
    public String addChatRecord(@RequestBody ChatMsg chatMsg) {
        if(chatMsg.getPostUserId()==null){
            return ResultUtil.jsonToStringSuccess();
        }

        long now = new Date().getTime();
        //防止点击多次，多次添加
        if(now-date.getTime()<1000){
            return ResultUtil.jsonToStringSuccess();
        }
        date=new Date();
        Integer result = chatMsgService.addChatRecord(chatMsg);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @PutMapping("call/updateChatRecordStatusBySeqId")
    @ApiOperation(value = "更新单条语音的已读未读状态", notes = "更新单条语音的已读未读状态")
    public String updateMsgBySeqId(@RequestParam("seqId")  String seqId) {
        Integer result=chatMsgService.updateMsgStatus(seqId);
        return result > 0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }







}
