package com.kiylab.spring_basic.repository;

import com.kiylab.spring_basic.domain.dto.MemoDTO;
import com.kiylab.spring_basic.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo,Long> {
  void deleteMemoByText(String text);
  List<Memo> findMemoByText(String text);

  List<Memo> findByMnoBetweenOrderByMnoDesc(Long mno1, Long mno2);

  Page<Memo> findByMnoBetween(Long mno1, Long mno2, Pageable pageable);

  long countByMno(Long mno);  // groupby로 알아서 묶어주는거임.ㄴ

//  long selectByMno(Long mno);
  List<Memo> findByMnoOrText(Long mno, String text);

  @Query("select m from Memo m order by m.mno desc limit 10")
  List<Memo> getListDesc();

  @Query(value = "select * from tbl_memo order by mno desc limit 10", nativeQuery = true)
  List<Memo> getListDesc2();

  @Transactional
  @Modifying
  @Query("update Memo m set m.text = :text where m.mno = :mno")
  int updateMemoText(@Param("mno") Long mno,@Param("text") String text);

  @Transactional
  @Modifying
  @Query("update Memo m set m.text = :#{#memo.text} where m.mno = :#{#memo.mno}")
  int updateMemoText2(Memo memo);

  @Transactional
  @Modifying
  @Query("update Memo m set m.text = ?2 where m.mno = ?1")
  int updateMemoText3(Long mno, String memoText);


  @Query(value = "select m.mno, m.text, CURRENT DATE from Memo m where m.mno > :mno"
          ,countQuery = "select count(m) from Memo m where m.mno > :mno")
  Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);
  // 배열은 좀 위험함. 타입도 불분명함

  @Query(value = "select m.mno AS mno, m.text AS text, CURRENT DATE from Memo m where m.mno > :mno"
          ,countQuery = "select count(m) from Memo m where m.mno > :mno")
  Page<MemoDTO> getListWithQueryProjection(Long mno, Pageable pageable);

}
