package com.ooad.dormitory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hello")
@CrossOrigin
public class HelloController {

  @Autowired
  ObjectMapper objectMapper;

  @GetMapping("")
  public Map<String, String> hello() {
    Map<String, String> res = Map.of("response", "hello-changed");
    return res;
  }
}
