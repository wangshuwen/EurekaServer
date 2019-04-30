package com.cst.xinhe.staffgroupterminal.service.service.impl;

import com.cst.xinhe.persistence.dao.staff.StaffJobMapper;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.staffgroupterminal.service.service.StaffJobService;
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
}
