package com.securitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.securitydemo.entity.User;

import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper  extends BaseMapper<User> {
}
