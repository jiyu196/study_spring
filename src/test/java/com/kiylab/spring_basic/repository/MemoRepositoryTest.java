package com.kiylab.spring_basic.repository;

import com.kiylab.spring_basic.entity.Memo;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class MemoRepositoryTest {
  @Autowired
  MemoRepository memoRepository;

  @Autowired
  private EntityManager entityManager;

  @Test
  @Transactional
  @Rollback(false)
  public void testEntityManager() {
    log.info("{}", entityManager);

    entityManager.persist(new Memo());
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testEntityManager2() {
//    Memo memo = memoRepository.findById(3L).orElseThrow(RuntimeException::new);
//    memo.setText("Hello World");

    //JPA는 dirty checking을 통해 값 변경 감지
    Memo memo = entityManager.find(Memo.class, 2L);  //영속 객체
    memo.setText("안녕");
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testEntityManager3() {
    Memo memo = new Memo();
    memo.setMno(2L);
    memo.setText("비영속");

    entityManager.merge(memo);  // 이럴게되면 비영속 객체랑 비교해서? .. 하게됨
//    entityManager.persist(memo);  // 마킹이 안되어있음.
  }



  @Test
  public void testExist() {
    log.info("testExist {}", memoRepository);
  }

  @Test
  public void testInsert() {
//    Memo memo = new Memo();
    Memo memo = Memo.builder().text("텍스트 확인").build();
    memoRepository.save(memo);   // memoRepository.save(new Memo);
    log.info("testInsert {}", memo);
  }

  @Test
  public void testFindAll() {
    memoRepository.findAll().forEach(m -> log.info("{}",m));
  }

  @Test
  public void testDelete() {
    Memo memo = entityManager.find(Memo.class, 2L);
    memoRepository.delete(memo);
  }
}
