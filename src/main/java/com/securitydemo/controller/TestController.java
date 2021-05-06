package com.securitydemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther ChenShuHong
 * @Date 2021-04-14 11:39
 */
@RestController
@RequestMapping("/test")
public class TestController {


  @GetMapping("hello")
  public String add(){
    return  "hello security";
  }

  @PostMapping("index")
  public String index(){
    return  "hello security  index";
  }

  @GetMapping("update")
  //注解,有该权限的才能访问
  @Secured({"ROLE_sale"})
  @PreAuthorize("hasRole('ROLE_sale')")
  public String update(){
    return  "hello security  update";
  }

}
