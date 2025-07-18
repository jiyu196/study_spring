package com.kiylab.spring_basic.pointcut;

import com.kiylab.spring_basic.service.FirstService;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MySimplePointcut extends StaticMethodMatcherPointcut {
  @Override
  public boolean matches(Method method, Class<?> targetClass) {
    // 매개변수 갯수가 1개 그리고 리턴타입이 void인 조건식을 만들어라
//    return method.getReturnType() == void.class && method.getParameterCount() == 1;
//    System.out.println(method.getAnnotatedReturnType());
//    //매서드의 이름이 two이고 FirstClass 타입
//    return true;
    return  method.getName().equals("two") && targetClass == FirstService.class;
  }

}
