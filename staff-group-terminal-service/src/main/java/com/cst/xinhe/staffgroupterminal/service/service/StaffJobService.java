package com.cst.xinhe.staffgroupterminal.service.service;


import com.cst.xinhe.persistence.model.staff.StaffJob;

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
}
