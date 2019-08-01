package com.cst.xinhe.web.service.wechat.service;

import com.cst.xinhe.persistence.model.wechat.WxUser;
import com.cst.xinhe.persistence.vo.wechat.GetPwd;

public interface WeChatService {

    String register(WxUser wxUser);

    String login(WxUser wxUser);

    void logout(String key);

    Integer updateSendMsgFlag(String key, Integer flag);

    String getPersonInfo(String key);

    String forgetPassword(GetPwd getPwd);
}
