package com.kiylab.spring_basic.app.advice;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

@Component
public class MyThrowAdvice implements ThrowsAdvice {
  public void afterThrowing(Throwable throwable) throws  Throwable {
    System.out.println("나는 afterThrow 야");
  }
}
