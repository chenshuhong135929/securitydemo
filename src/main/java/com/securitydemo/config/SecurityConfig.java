package com.securitydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Auther ChenShuHong
 * @Date 2021-04-14 16:46
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  UserDetailsService userDetailsService;

  //注入数据源
  @Resource
  DataSource dataSource;

  @Bean
  public PersistentTokenRepository persistentTokenRepository(){
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    jdbcTokenRepository.setDataSource(dataSource);
    //没有创建表可以定义初始化创建
    //jdbcTokenRepository.setCreateTableOnStartup(true);
    return jdbcTokenRepository;
  }

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  protected void configure( HttpSecurity http) throws Exception {
    http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();
    //没有权限，自定义页面
    http.exceptionHandling().accessDeniedPage("/unauth.html");
    http.formLogin() //自定义自己的登陆界面
    .loginPage("/login.html")//登陆界面的地址
    .loginProcessingUrl("/user/login")//登陆访问路径
   // .successForwardUrl("/test/index").permitAll()//登陆成功后跳转路径
        .defaultSuccessUrl("/success.html").permitAll()
    .and().authorizeRequests()
        .antMatchers("/","/test/hello","/user/login").permitAll()//那些路径不需要保护可以直接访问
        //hasAuthority支持单个   hasAnyAuthority支持多个
        // .antMatchers("/test/index").hasAnyAuthority("admins")//当前登录的用户，只有admin权限才可以进行访问
        .antMatchers("/test/index").hasAnyRole("save")//当前用户必须有role角色才能访问
    .anyRequest().authenticated()
        //记住我
        .and().rememberMe().tokenRepository(persistentTokenRepository())
        //时长  秒为单位
        .tokenValiditySeconds(60)
        //查询的接口
        .userDetailsService(userDetailsService)
    .and().csrf().disable();//关闭CSRF防护
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return  new BCryptPasswordEncoder();
  }




}
