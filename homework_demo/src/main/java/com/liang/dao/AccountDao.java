package com.liang.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    //用户名密码验证
    public boolean check(String username,String password){
        String sql="select password from taskdemo.account where username = ?";
        String result=jdbcTemplate.queryForObject(sql,new Object[]{username},String.class);
        if (password.equals(result)) return true;
        else return false;
    }

}
