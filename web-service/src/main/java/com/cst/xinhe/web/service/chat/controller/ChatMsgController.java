package com.cst.xinhe.web.service.chat.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;

import com.cst.xinhe.common.constant.ConstantUrl;
import com.cst.xinhe.persistence.dto.chat_msg.ChatMsgHistoryDto;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.web.service.chat.service.ChatMessageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;


import io.swagger.annotations.ApiOperation;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @GetMapping("call/singleVoiceNum")
    @ApiOperation(value = "获取当前未读单条语音的数量", notes = "获取当前未读单条语音的数量")
    public String singleVoiceNum(@Param("staffId") Integer staffId) {
        Integer count =chatMsgService.getSingleVoiceNum(staffId);
        return ResultUtil.jsonToStringSuccess(count);
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

    @GetMapping("call/chatRecord")
    @ApiOperation(value = "获取聊天记录", notes = "根据员工id，分页查询聊天记录")
    public String chatRecord( @RequestParam(name = "staffId") Integer staffId,
                             @RequestParam(name = "startPage", defaultValue = "1", required = false) Integer startPage,
                             @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        Page page = chatMsgService.findChatRecord(staffId,startPage,pageSize);
        List<HashMap<String,Object>> result = page.getResult();
        for (HashMap<String, Object> map : result) {
            String postMsg = (String) map.get("postMsg");
            if(null != postMsg){
                postMsg = postMsg.replace(ConstantUrl.basePath,ConstantUrl.webBaseUrl);
                map.put("postMsg",postMsg);
            }
        }

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
