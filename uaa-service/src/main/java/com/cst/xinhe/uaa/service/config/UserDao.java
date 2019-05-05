package com.cst.xinhe.uaa.service.config;


import com.cst.xinhe.uaa.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/30/10:25
 */
public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String userName);
}
