package com.kiylab.spring_basic.repository;

import com.kiylab.spring_basic.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class MemberRepositoryTest {
  @Autowired
  MemberRepository memberRepository;

  @Test
  public void testExist() {
    log.info("testExist : {}", memberRepository);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testInsert() {
    Member member = Member.builder().id("sae").pw("1234").age(20).name("새똥이").build();
    memberRepository.save(member);
  }

  @Test
  public void testFindById() {
    Member member = memberRepository.findById(1L).orElseThrow(() -> new RuntimeException("지정된 회원 번호가 없습니다"));
//    Member member1 = memberRepository.findBy(Example.of(Member.builder().build()), fetchableFluentQuery -> )
    log.info("testFindById : {}", member);
  }

  @Test
  public void testFindAll() {
    memberRepository.findAll().forEach(m -> log.info("{}",m));
  }

  @Test
  @Transactional
  @Rollback(false)
  public  void testUpdate() {
    Member member = memberRepository.findById(1l).orElseThrow(() -> new RuntimeException("지정된 회원번호가 없습니다."));
    member.setAge(30);
//    memberRepository.save(member);  save를 한다는건 insert 또는 update라는 뜻이라서 안쓰려고하심
  }

  @Test
  public void testDelete() {
    memberRepository.deleteById(2L);
  }
}
