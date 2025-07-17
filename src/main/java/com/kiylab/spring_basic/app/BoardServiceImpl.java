package com.kiylab.spring_basic.app;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service  //spring bean이 됨.
public class BoardServiceImpl implements BoardService{
  @Override
  public void write(String title, String content) {
    System.out.println(title + ", " + content);
    System.out.println("글쓰기 완료 ");
  }


  @Override
  public Object get(Long bno) {
    System.out.println(bno + "빈 글 조회 완료");
    return null;
  }

  @Override
  public void modify(String title, String content) {
    System.out.println(title + ", " + content);
    System.out.println("글 수정 완료");;
  }

  @Override
  public void remove(Long bno) {
    if(bno == 1L) throw new RuntimeException( "1번글 안받음");
    System.out.println(bno + "빈 글 삭제 완료");
//    System.out.println();
  }
}
