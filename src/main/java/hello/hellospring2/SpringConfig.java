package hello.hellospring2;

import hello.hellospring2.repository.MemberRepository;
import hello.hellospring2.repository.MemoryMemberRepository;
import hello.hellospring2.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Service, @Repository나 @Autowired (컴포넌트 스캔 방식) 사용하지 않고
// 수동으로 memberService랑 memberRepository를 스프링 빈에 등록해주는 방법 (자바 코드로 스프링 빈 설정)
@Configuration
public class SpringConfig {
    // 이 로직을 호출해서 스프링 빈에 등록해줌
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
