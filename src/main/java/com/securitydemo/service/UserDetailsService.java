package com.securitydemo.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.securitydemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther ChenShuHong
 * @Date 2021-04-27 14:07
 */
@Service
public class UserDetailsService  implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired
  UserMapper userMapper;

  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    //以下查询，换成数据库查询
    com.securitydemo.entity.User user = new LambdaQueryChainWrapper<>(userMapper)
        .eq(com.securitydemo.entity.User::getUserName, s).one();
    List<GrantedAuthority> authorities =AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_save");
    return new User(user.getUserName(),new BCryptPasswordEncoder().encode(user.getPassword()),authorities);
  }
}
