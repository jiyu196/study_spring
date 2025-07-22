package com.kiylab.spring_basic.repository;

import com.kiylab.spring_basic.domain.dto.MemoDTO;
import com.kiylab.spring_basic.entity.Memo;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

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
    IntStream.rangeClosed(1, 100).forEach(i -> {
    Memo memo = Memo.builder().text("sample + i").build();
    memoRepository.save(memo);   // memoRepository.save(new Memo);
    });
  }

  @Test
  public void testFindAll() {
    memoRepository.findAll().forEach(m -> log.info("{}",m));
  }

  @Test
  public void testUpdate() {  // 수정
    Memo memo = Memo.builder().mno(100L).text("텍스트 수정").build();
    memoRepository.save(memo);
  }

  @Test
  public void testDelete() {
    Memo memo = entityManager.find(Memo.class, 101L);
    memoRepository.delete(memo);
  }

  @Test
  public void testSelect() {
    Memo memo = memoRepository.findById(1L).orElse(null);
    log.info("{}", memo);
  }

  @Test
  public void testPageDefault() {
    PageRequest pageRequest = PageRequest.of(0,5);
    Page<Memo> result = memoRepository.findAll(pageRequest);
    result.forEach(r -> log.info("{}", r));

    long totalElememts = result.getTotalElements();
    int totalPages = result.getTotalPages();

    log.info("totalElements={}", totalElememts);
    log.info("totalPages={}", totalPages);

    //현재 페이지번호
    log.info("number={}", result.getNumber());
    //페이지당 데이터 갯수
    log.info("size={}", result.getSize());

    //다음 페이지 여부
    log.info("hasPrevious={}", result.hasPrevious());
    log.info("hasNext={}", result.hasPrevious());
    //시작 페이지 여부
    log.info("isFirst={}", result.isFirst());
    log.info("isLast={}", result.isLast());

    result.getContent().forEach(c -> log.info("{}", c));
  }

  @Test
  public void testSort() {
    Sort sort = Sort.by(Sort.Direction.DESC, "mno");
    PageRequest pageRequest = PageRequest.of(2, 5, sort);
    Page<Memo> result = memoRepository.findAll(pageRequest);
    result.forEach(r -> log.info("{}", r));

    //EAGER, LAZY(호출순서를 늦추는)
  }

  @Test
  public void testQueryMethod() {
    memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L).forEach(m -> log.info("{}", m));
  }

  @Test
  public void testQueryMethod2() {
    Page<Memo> memos = memoRepository
            .findByMnoBetween(10L, 50L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
    memos.forEach(m -> log.info("{}", m));
  }

  // memo의 총 갯수를 로깅하게끔 만들기. 이름조합을 통해서 만들어야함.
  @Test
  @Commit
  public void testCount() {
//    log.info("{}", memoRepository.count());
    log.info("{}", memoRepository.countByMno(1L));

    //Mmo가 특정 long이거나 memoText가 특정 문자열일때의 query method
    memoRepository.findByMnoOrText(5L, "기본값").forEach(m -> log.info("{}", m));

  }

  @Test
  public void testList() {
    log.info("{}", memoRepository.getListDesc());
    log.info("{}", memoRepository.getListDesc2());
  }

  @Transactional
  @Commit
  @Test
  public void testUpdateText() {
    Memo memo = memoRepository.findById(2L).orElseThrow();
    memo.setText("변경내용 1");
  }

  @Test
  public void testUpdateText2() {
//    Memo memo = memoRepository.findById(2L).orElseThrow();
    memoRepository.updateMemoText(3L, "변경내용 2");
  }

  @Test
  public void testUpdateText3() {
//    Memo memo = memoRepository.findById(2L).orElseThrow();
    memoRepository.updateMemoText2(Memo.builder().mno(5L).text("변경내용3").build());
  }

  @Test
  public void testUpdateText4() {
//    Memo memo = memoRepository.findById(2L).orElseThrow();
    memoRepository.updateMemoText3(6L, "순서찾기로 Pram 생략");
  }

  @Test
  public void testListWithQueryObject() {
      Page<Object[]> objects = memoRepository.getListWithQueryObject(
              0L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
      objects.forEach(r -> {
        for(Object o : r) {
          log.info("{}", o);
        }
      });
    }

    @Test
  public void testListWithQueryProjection() {
    Page<MemoDTO> dtos = memoRepository.getListWithQueryProjection(0L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
    dtos.forEach(r -> log.info("{} {} {}", r.getMno(), r.getText(), r.getN()));
    }

}
