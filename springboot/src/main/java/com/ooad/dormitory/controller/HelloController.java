package com.ooad.dormitory.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@CrossOrigin
public class HelloController {

//  @Autowired
//  Object obj;

  @GetMapping("")
  public String hello() {
    return "Hello";
  }
}
