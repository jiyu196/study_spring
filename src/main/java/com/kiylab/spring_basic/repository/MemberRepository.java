package com.kiylab.spring_basic.repository;

import com.kiylab.spring_basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // 레포지토리로 등록. 스프링 jpa한테 알리는거.
public interface MemberRepository extends JpaRepository<Member,Long> {
}
