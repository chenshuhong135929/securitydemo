package com.securitydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @Auther ChenShuHong
 * @Date 2021-04-14 11:40
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true)
@MapperScan("com.securitydemo.mapper")
public class Securitydemo {
  public static void main(String[] args)
  {
    SpringApplication.run(Securitydemo.class, args);
  }

}
