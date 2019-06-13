package com.cst.xinhe.web.service.staff_group_terminal.service;


import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName StaffJobService
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/12 15:03
 * @Vserion v0.0.1
 */

public interface StaffJobService {
    List<StaffJob> getAllJobs();

    String findJobNameById(Integer staffJobId);

    StaffJob findJobById(Integer jobId);

    PageInfo getStaffJobsByParams(Integer pageSize, Integer startPage, String jobName);

    Integer updateJob(StaffJob staffJob);

    Integer delStaffJob(Integer jobId);

    Integer addStaffJobs(StaffJob staffJob);
}
