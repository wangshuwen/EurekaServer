package com.userservice.userservice.service;

import com.userservice.userservice.Utils.BPwdEncoderUtil;
import com.userservice.userservice.dao.UserDao;
import com.userservice.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/30/16:06
 */
@Service
public class UserServiceDetail {
    @Autowired
    private UserDao userDao;

    public User insertUser(String username,String password){
        User user = new User();
        user.setUserName(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        return userDao.save(user);
    }

}
