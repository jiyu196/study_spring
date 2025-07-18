package com.kiylab.spring_basic.service;

import com.kiylab.spring_basic.app.BoardService;
import org.springframework.stereotype.Service;

@Service
public class FirstService {

  public void one() {
    System.out.println("First.one()");
  }


  public void two() {

    System.out.println("First.two()");
  }
}
