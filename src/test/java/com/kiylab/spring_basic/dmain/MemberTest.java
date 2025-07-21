package com.kiylab.spring_basic.dmain;

import com.kiylab.spring_basic.config.AppConfig;
import com.kiylab.spring_basic.domain.Member;
import com.kiylab.spring_basic.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Slf4j
@ContextConfiguration(classes = AppConfig.class)
public class MemberTest {
  @Autowired
  MemberRepository memberRepository;

  @Test
  public void testExist() {
    log.info("{}", memberRepository);
  }

}
