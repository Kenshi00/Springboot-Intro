package hello.hellospring2.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
// AOP를 왜 사용하는가?
// 회원가입, 회원조회 등 핵심 관심사항과 시간을 측정하는 공통관심사항 분리
// 시간 측정하는 로직을 별도의 공통 로직으로 만듬
// 핵심 관심 사항을 깔끔하게 유지하고, 공통 관심 사항의 변경이 필요하면 이 로직만 변경
@Aspect
@Component
public class TimeTraceAop {
    // hello.hellospring2에 다 적용해라!
    @Around("execution(* hello.hellospring2..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        }finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " "+timeMs + "ms");
        }
    }
}
