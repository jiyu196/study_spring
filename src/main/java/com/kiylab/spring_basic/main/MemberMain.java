package com.kiylab.spring_basic.main;

import com.kiylab.spring_basic.domain.Member;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MemberMain {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("xml/bean-config.xml");
    Member m = context.getBean("member", Member.class);
    System.out.println(m);
    // xml설정정리후 bean으로 등록함.
  }
}
