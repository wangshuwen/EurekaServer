package com.userservice.userservice.dao;

import com.userservice.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/30/16:02
 */
public interface UserDao extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
