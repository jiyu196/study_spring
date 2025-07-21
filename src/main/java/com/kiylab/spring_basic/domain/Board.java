package com.kiylab.spring_basic.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_board")
public class Board {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  //이게 키 값을 주는걸껄?
  private Long bno;

  @Column(length = 1000, nullable = true)  // 1000바이트 크기가됨
  private String title;

  @Column(columnDefinition = "text")  // varchar는 4000바이트까지됨.
  private String content;

  private LocalDateTime regdate;

  @ManyToOne
  @JoinColumn(name = "user_no")
  private Member member;
}
