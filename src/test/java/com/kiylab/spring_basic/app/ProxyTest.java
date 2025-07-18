package com.kiylab.spring_basic.app;

import com.kiylab.spring_basic.app.advice.MyBeforeAdvice;
import com.kiylab.spring_basic.app.advice.MyAroundAdvice;
import com.kiylab.spring_basic.service.FirstService;
import com.kiylab.spring_basic.service.SecondService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

@SpringBootTest
@Slf4j
public class ProxyTest {
  @Autowired
  private  BoardService boardService;
  @Autowired
  private MyAroundAdvice advice;
  @Autowired
  private MyBeforeAdvice beforeAdvice;
  @Autowired
  private AfterReturningAdvice afterReturn;


  @Autowired
  private Pointcut pointcut;

  @Autowired
  private ThrowsAdvice throwsAdvice;


  private BoardService proxy;
  @PostConstruct
  public void init() {
    Advice[] advices = new Advice[]{afterReturn, throwsAdvice};
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);
    for(Advice a : advices) {
      proxyFactory.addAdvice(a);
    }
    proxy = (BoardService) proxyFactory.getProxy();
  };  //프록시타입받아서 해야하는

  @Test
  public void testExist() {
    log.info("{}", boardService);
  }  //null인지 아닌지 확인
  @Test
  public void testWrite() {
    boardService.write("원본 객체의 제목", "내용");

    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);
    proxyFactory.addAdvice(advice);
    BoardService proxy = (BoardService)proxyFactory.getProxy();

    proxy.write("프록시 객체의 제목", "내용");
  }

  @Test
  public void testBefore() {
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);
    proxyFactory.addAdvice(beforeAdvice);
    proxyFactory.addAdvice(advice);
    BoardService proxy = (BoardService)proxyFactory.getProxy();

    proxy.write("프록시 객체의 제목", "내용");

  System.out.println("==============글쓰기===================");
  proxy.write("프록시 객체의 제목", "내용");

  System.out.println("==============글조회===================");
  proxy.get(3L);
  }

  @Test
  public void testAfterReturn() {
    try {
      proxy.remove(1L);
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testAdvisor() {
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    //Advisor advisor
    PointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
    proxyFactory.addAdvisor(pointcutAdvisor);

    proxy = (BoardService) proxyFactory.getProxy();

    proxy.write("제목", "내용");
    proxy.get(3L);
    proxy.remove(4L);
  }

  @Test
  public void testSecondAdvice() {
    MethodBeforeAdvice beforeAdvice = (method, args, target) -> System.out.println("익명 출력");

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new StaticMethodMatcherPointcut() {
      @Override
      public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().equals("two") && targetClass == FirstService.class;
      }
    }, beforeAdvice);
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(firstService);
    proxyFactory.addAdvisor(advisor);

    ProxyFactory proxyFactory2 = new ProxyFactory();
    proxyFactory2.setTarget(secondService);
    proxyFactory2.addAdvisor(advisor);

    FirstService proxy = (FirstService) proxyFactory.getProxy();
    proxy.one();
    proxy.two();

    SecondService proxy2 = (SecondService) proxyFactory2.getProxy();
    proxy2.one();
    proxy2.two();
    }

  @Autowired
  private FirstService firstService;

  @Autowired
  private SecondService secondService;

  @Test
  public void testAspectj() {
    AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
    pc.setExpression("execution(void* *.write*(..))");  //표현식을 통한 조건을 정의해줘야함.
    //모든 메서드의 식을 다 가로채겠다는 뜻.  *반환타입,*클래스명, *메서드명
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pc, beforeAdvice);

    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    proxyFactory.addAdvisor(advisor);

    BoardService proxy = (BoardService) proxyFactory.getProxy();
    proxy.write("title", "content");
    proxy.get(3L);
  }
  }
