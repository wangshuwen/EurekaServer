package com.userservice.userservice.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/30/11:06
 */
@Entity
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
