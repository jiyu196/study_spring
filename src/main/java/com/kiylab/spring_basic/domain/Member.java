package com.kiylab.spring_basic.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Member {
  @Value("gae-ddong")
  private String name;
  @Value("32")
  private int age;
}
