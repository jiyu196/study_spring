package com.kiylab.spring_basic.main;

import com.kiylab.spring_basic.domain.Member;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MemberMain2 {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("xml/bean-config-java.xml");
    Member m = context.getBean("member", Member.class);

    Member m2 = context.getBean("member", Member.class); // d이 두개는 같은값. true로 나옴. 그래서 위에께 값이 바뀌면 밑에도 바뀜
    System.out.println(m == m2);
    // xml설정정리후 bean으로 등록함.
  }
}
