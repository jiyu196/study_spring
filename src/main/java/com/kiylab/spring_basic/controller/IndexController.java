package com.kiylab.spring_basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class IndexController {
  @GetMapping("")
  @ResponseBody // 없으면 jsp forward
  public Map<?,?> index() {  // ? 가 생략하면 object임.
    return Map.of("test", 123456);
  }
}
