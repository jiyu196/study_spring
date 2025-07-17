package com.kiylab.spring_basic.app;

import java.lang.reflect.Proxy;

public class App {
  public static void main(String[] args) {
    BoardService target = new BoardServiceImpl();
    System.out.println("==============target의 결과물===================");
    target.write("제목", "내용");

    BoardService proxy = (BoardService) Proxy.newProxyInstance(
            BoardService.class.getClassLoader(),
            new Class[] {BoardService.class},
            new LoggingInvocationHandler(target)
    );

    System.out.println("==============target의 결과물===================");
    proxy.write("title", "content");
  }
}
