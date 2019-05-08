package com.cst.xinhe.system.service.service.impl;

import com.cst.xinhe.persistence.dao.user.SysUserMapper;
import com.cst.xinhe.persistence.model.user.SysUser;
import com.cst.xinhe.persistence.model.user.SysUserExample;
import com.cst.xinhe.persistence.vo.UserLoginVOReq;
import com.cst.xinhe.persistence.vo.UserLoginVOResp;
import com.cst.xinhe.system.service.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/5/6/17:30
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public UserLoginVOResp userLogin(UserLoginVOReq userLoginVOReq) {
        UserLoginVOResp resp = null;

        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andSysAccountEqualTo(userLoginVOReq.getAccount());
        List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
        if(list!=null&&list.size()>0){
            SysUser user = list.get(0);
            //封装返回数据
            resp = new UserLoginVOResp();
            resp.setAccount(user.getSysAccount());
            resp.setUserId(user.getSysUserid());
            resp.setUserName(user.getSysUsername());
            resp.setHeadimg(user.getSysHeadimg());
            resp.setLastLoginTime(user.getSysLastLoginTime());
            resp.setPhone(user.getSysPhonenumber());
        }

        return resp;

    }
}
