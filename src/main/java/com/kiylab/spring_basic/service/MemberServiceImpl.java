package com.kiylab.spring_basic.service;

import com.kiylab.spring_basic.domain.Member;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mem1")
@ToString
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
//  @Autowired
//  @NonNull
//  private final Member member;  // 이것만 있으면 null

//  @Autowired  // 이게 생성자 주입
//  public MemberServiceImpl(Member member) { //생성자 정의
//    this.member = member;
//  }

  @Override
  public void register(Member member) {

  }
}
