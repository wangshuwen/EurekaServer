package com.cst.xinhe.web.service.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.utils.string.StringUtils;
import com.cst.xinhe.persistence.dao.staff.StaffJobMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.wechat.WxUserMapper;
import com.cst.xinhe.persistence.dto.staff.StaffInfoDto;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffExample;
import com.cst.xinhe.persistence.model.wechat.WxUser;
import com.cst.xinhe.persistence.model.wechat.WxUserExample;
import com.cst.xinhe.persistence.vo.wechat.GetPwd;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffOrganizationService;
import com.cst.xinhe.web.service.system.utils.RedisUtils;
import com.cst.xinhe.web.service.wechat.service.WeChatService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-07-04 13:52
 **/
@Service
public class WeChatServiceImpl implements WeChatService {

    @Resource
    WxUserMapper wxUserMapper;

    @Resource
    StaffMapper staffMapper;

    @Resource
    StaffOrganizationService staffOrganizationService;

    @Resource
    StaffJobMapper staffJobMapper;

    @Resource
    RedisUtils redisUtils;

    @Override
    public String register(WxUser wxUser) {
        String account = wxUser.getWxUserAccount().trim();
        WxUserExample wxUserExample = new WxUserExample();
        WxUserExample.Criteria criteria = wxUserExample.createCriteria();
        criteria.andWxUserAccountEqualTo(account);
        List<WxUser> wxUserList =  wxUserMapper.selectByExample(wxUserExample);
        if (null != wxUserList && !wxUserList.isEmpty()){
            //已存在，不需要注册
            return ResultUtil.jsonToStringError(ResultEnum.ACCOUNT_IS_EXISTS);
        }else {
            StaffExample staffExample = new StaffExample();
            StaffExample.Criteria criteria1 = staffExample.createCriteria();
            criteria1.andIsPersonEqualTo(1);
            criteria1.andStaffPhoneEqualTo(account);
            List<Staff> list = staffMapper.selectByExample(staffExample);
            if (null != list && !list.isEmpty()){
                //可以注册
                Md5Hash md5Hash = new Md5Hash(wxUser.getWxUserPassword(), account);
                md5Hash = new Md5Hash(md5Hash);
                wxUser.setWxUserPassword(md5Hash.toString());
                wxUser.setCreateTime(new Date());
                wxUser.setSendMsgFlag(0);
                wxUser.setStaffId(list.get(0).getStaffId());
                wxUserMapper.insertSelective(wxUser);
                return ResultUtil.jsonToStringSuccess(wxUser);
            }else {
                //不权限注册
                return ResultUtil.jsonToStringError(ResultEnum.ACCOUNT_HAS_NOT_AUTH);
            }
        }
    }

    @Override
    public String login(WxUser wxUser) {

        String account = wxUser.getWxUserAccount().trim();
        String password = wxUser.getWxUserPassword().trim();
        if (StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(password)){
//            if(redisUtils.hasKey(account)){
//                return ResultUtil.jsonToStringError(ResultEnum.THIS_ACCOUNT_IS_LOGIN);
//            }
            WxUserExample wxUserExample = new WxUserExample();
            WxUserExample.Criteria criteria = wxUserExample.createCriteria();
            criteria.andWxUserAccountEqualTo(account);
            List<WxUser> list = wxUserMapper.selectByExample(wxUserExample);
            if (null != list && !list.isEmpty()){
                //进一步判断
                Md5Hash md5Hash = new Md5Hash(password, account);
                md5Hash = new Md5Hash(md5Hash);
                if ((md5Hash.toString()).equals(list.get(0).getWxUserPassword())){
                    //登陆成功
                    StaffInfoDto staffInfoDto = new StaffInfoDto();
                    Integer staffId = list.get(0).getStaffId();
                    Staff staff = staffMapper.selectByPrimaryKey(staffId);
                    if (null == staff){
                        return ResultUtil.jsonToStringError(ResultEnum.FAILED);
                    }
                    staffInfoDto.setDeptId(staff.getGroupId());
                    staffInfoDto.setDeptName(staffOrganizationService.getDeptNameByGroupId(staff.getGroupId()));
                    staffInfoDto.setStaffName(staff.getStaffName());
                    staffInfoDto.setIsPerson(staff.getIsPerson());
                    staffInfoDto.setStaffPhone(account);
                    staffInfoDto.setStaffSex(staff.getStaffSex());
                    staffInfoDto.setStaffIdCard(staff.getStaffIdCard());
                    staffInfoDto.setJobId(staff.getStaffJobId());
                    staffInfoDto.setJobName(staffJobMapper.selectByPrimaryKey(staff.getStaffJobId()).getJobName());
                    staffInfoDto.setRemark(list.get(0).getSendMsgFlag().toString());
                    redisUtils.set(account, JSON.toJSONString(staffInfoDto),1L);
                    return ResultUtil.jsonToStringSuccess(staffInfoDto);
                }else{
                    return ResultUtil.jsonToStringError(ResultEnum.ACCOUNT_OR_PWD_IS_ERROR);
                }
            }else {
                return ResultUtil.jsonToStringError(ResultEnum.ACCOUNT_OR_PWD_IS_ERROR);
            }
        }else {
            return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
        }

    }

    @Override
    public void logout(String key) {
        redisUtils.delete(key);
    }

    @Override
    public Integer updateSendMsgFlag(String key, Integer flag) {
        redisUtils.updateExpireTime(key,1L);
        WxUser wxUser = new WxUser();
        wxUser.setSendMsgFlag(flag);
        WxUserExample wxUserExample = new WxUserExample();
        WxUserExample.Criteria criteria = wxUserExample.createCriteria();
        criteria.andWxUserAccountEqualTo(key);
        return wxUserMapper.updateByExampleSelective(wxUser,wxUserExample);
    }

    @Override
    public String getPersonInfo(String key) {
        WxUserExample wxUserExample = new WxUserExample();
        WxUserExample.Criteria criteria = wxUserExample.createCriteria();
        criteria.andWxUserAccountEqualTo(key);
        List<WxUser> list = wxUserMapper.selectByExample(wxUserExample);
        if (null != list && !list.isEmpty()){
            redisUtils.updateExpireTime(key,1L);
//            if (redisUtils.hasKey(key)){
//                redisUtils.updateExpireTime(key,1L);
//                return ResultUtil.jsonToStringSuccess(redisUtils.get(key));
//            }
            StaffInfoDto staffInfoDto = new StaffInfoDto();
            Integer staffId = list.get(0).getStaffId();
            Staff staff = staffMapper.selectByPrimaryKey(staffId);
            if (null == staff){
                return ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
            }
            staffInfoDto.setDeptId(staff.getGroupId());
            staffInfoDto.setDeptName(staffOrganizationService.getDeptNameByGroupId(staff.getGroupId()));
            staffInfoDto.setStaffName(staff.getStaffName());
            staffInfoDto.setIsPerson(staff.getIsPerson());
            staffInfoDto.setStaffPhone(key);
            staffInfoDto.setStaffSex(staff.getStaffSex());
            staffInfoDto.setStaffIdCard(staff.getStaffIdCard());
            staffInfoDto.setJobId(staff.getStaffJobId());
            staffInfoDto.setJobName(staffJobMapper.selectByPrimaryKey(staff.getStaffJobId()).getJobName());
            staffInfoDto.setRemark(list.get(0).getSendMsgFlag().toString());
            return ResultUtil.jsonToStringSuccess(staffInfoDto);
        }else {
            return ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
        }

    }

    @Override
    public String forgetPassword(GetPwd getPwd) {
        String account = getPwd.getAccount();
        WxUserExample wxUserExample = new WxUserExample();
        WxUserExample.Criteria criteria = wxUserExample.createCriteria();
        criteria.andWxUserAccountEqualTo(account);
        List<WxUser> list = wxUserMapper.selectByExample(wxUserExample);
        if (null != list && !list.isEmpty()){
            Integer staffId = list.get(0).getStaffId();
            Staff staff = staffMapper.selectByPrimaryKey(staffId);
            if (getPwd.getIdCard().equals(staff.getStaffIdCard())){
                WxUser wxUser = new WxUser();
                String password = getPwd.getNewPassword();
                Md5Hash md5Hash = new Md5Hash(password, account);
                md5Hash = new Md5Hash(md5Hash);
                wxUser.setWxUserPassword(md5Hash.toString());
                int i = wxUserMapper.updateByExampleSelective(wxUser,wxUserExample);
                if (i == 1){
                    return ResultUtil.jsonToStringSuccess();
                }else {
                    return ResultUtil.jsonToStringError(ResultEnum.FAILED);
                }
            }else {
                return ResultUtil.jsonToStringError(ResultEnum.ID_CARD_INFO_ERROR);
            }
        }else {
            return ResultUtil.jsonToStringError(ResultEnum.ACCOUNT_IS_NOT_EXISTS);
        }
    }
}
