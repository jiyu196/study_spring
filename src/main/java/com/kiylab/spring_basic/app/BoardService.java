package com.kiylab.spring_basic.app;

public interface BoardService {
  void write(String title, String content);
  Object get(Long bno);
//  void list(String title, String content);
  void modify(String title, String content);
  void remove(Long bno);

}
