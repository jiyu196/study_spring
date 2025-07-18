package com.kiylab.spring_basic.app;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
  @Before(value = "execution(* com..*.get(..))")
  public  void  before(JoinPoint joinPoint) {
    System.out.println(joinPoint.getSignature().getName() + "before 처리됨");
  }

  @Around("execution(* com..*.get(Long))")
  public Object around(ProceedingJoinPoint joinPoint) throws  Throwable {
    System.out.println("around's before");
    Object ret = joinPoint.proceed();
    System.out.println("around's after");
    return ret;
  }

  @After("bean(boardServiceImpl)")  // boardServiceImpl에 해당하는 것들을 찾아라
  public void after(JoinPoint joinPoint) {
    System.out.println("after finally");  //예외가 뜨던 아니던 실행해라.
  }

  @AfterReturning("args(String, String) && execution(* *..BoardServiceImpl.*(..))")
  public  void afterReturn(JoinPoint joinPoint) {
    System.out.println("정상 종ㄽ afterReturning");
  }
}
