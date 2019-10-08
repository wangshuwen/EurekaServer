package com.cst.xinhe.persistence.dao.chat;

import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.persistence.model.chat.ChatMsgExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ChatMsgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    long countByExample(ChatMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    int deleteByExample(ChatMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer msgId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    int insert(ChatMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    int insertSelective(ChatMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    List<ChatMsg> selectByExample(ChatMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    ChatMsg selectByPrimaryKey(Integer msgId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ChatMsg record, @Param("example") ChatMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ChatMsg record, @Param("example") ChatMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ChatMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chat_msg
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ChatMsg record);

    List<Map<String, Object>> selectMsgByUserIdAndStaffId(@Param("userId") Integer userId, @Param("staffId") Integer staffId);

    List<Map<String, Object>> findChatList(@Param("keyWord") String keyWord, @Param("deptIds") List<Integer> deptIds);


    List<Map<String,Object>> findChatRecord(Integer staffId);

    Integer deleteChatRecord(@Param("staffId") Integer staffId);

    Integer updateChatMegStatusBySeqId(ChatMsg chatMsg);

    Integer findUnReadCount(Integer staffId);

    Integer getSingleVoiceNum(@Param("staffId") Integer staffId);
}
