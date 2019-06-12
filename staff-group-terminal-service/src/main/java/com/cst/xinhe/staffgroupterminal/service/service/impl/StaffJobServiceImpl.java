package com.cst.xinhe.staffgroupterminal.service.service.impl;

import com.cst.xinhe.persistence.dao.staff.StaffJobMapper;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.staffgroupterminal.service.service.StaffJobService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName StaffJobServiceImpl
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/12 15:04
 * @Vserion v0.0.1
 */

@Service
public class StaffJobServiceImpl implements StaffJobService {


    @Resource
    private StaffJobMapper staffJobMapper;

    @Override
    public List<StaffJob> getAllJobs() {
        return staffJobMapper.selectAllJobs();
    }

    @Override
    public String findJobNameById(Integer staffJobId) {
        StaffJob staffJob = staffJobMapper.selectByPrimaryKey(staffJobId);
        return staffJob != null ? staffJob.getJobName(): null;
    }

    @Override
    public StaffJob findJobById(Integer jobId) {
        return staffJobMapper.selectByPrimaryKey(jobId);
    }

    @Override
    public PageInfo getStaffJobsByParams(Integer pageSize, Integer startPage, String jobName) {

        Page<StaffJob> page = PageHelper.startPage(startPage,pageSize);
        List<StaffJob> list = staffJobMapper.selectJobByJobName(jobName);
        PageInfo<StaffJob> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }


    @Override
    public Integer updateJob(StaffJob staffJob) {

        return staffJobMapper.updateByPrimaryKeySelective(staffJob);
    }

    @Override
    public Integer delStaffJob(Integer jobId) {
        return staffJobMapper.deleteByPrimaryKey(jobId);
    }

    @Override
    public Integer addStaffJobs(StaffJob staffJob) {
        return staffJobMapper.insertSelective(staffJob);
    }
}
