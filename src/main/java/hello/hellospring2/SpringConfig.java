package hello.hellospring2;

import hello.hellospring2.aop.TimeTraceAop;
import hello.hellospring2.repository.*;
import hello.hellospring2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Service, @Repository나 @Autowired (컴포넌트 스캔 방식) 사용하지 않고
// 수동으로 memberService랑 memberRepository를 스프링 빈에 등록해주는 방법 (자바 코드로 스프링 빈 설정)
@Configuration
public class SpringConfig {
    // 이 로직을 호출해서 스프링 빈에 등록해줌

//    private DataSource dataSource;
//
//    // 생성자 DI 주입을 해줌
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    // jpa version
//    private EntityManager em;
//    // jpa에서도 생성자 DI 주입
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }
    // SpringDataJpa에 의해 이미 bean에 등록되어 있는 상태
    private final MemberRepository memberRepository;
    // 생성자 DI 주입
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }
//    @Bean
//    public MemberRepository memberRepository(){
//        // return new MemoryMemberRepository();
//        // return new JdbcMemberRepository(dataSource);
//        // return new JdbcTemplateMemberRepository(dataSource);
//        // return new JpaMemberRepository(em);
//    }
    // TimeTraceAop에다 @Component 붙여서 쉽게 할 수도 있지만
    // 이렇게 해놓으면 Aop가 있다는걸 명시할 수 있음.
//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }
}
