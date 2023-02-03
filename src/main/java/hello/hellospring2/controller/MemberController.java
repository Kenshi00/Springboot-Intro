package hello.hellospring2.controller;

import hello.hellospring2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

// @Controller를 붙이면 스프링이 뜰 때 MemberController 객체를 생성해서 스프링 통에 들고 있다.
// 이것을 스프링 컨테이너에서 빈이 관리된다고 표현함.
// @Controller-@Service-@Repository와 같은 방식을 컴포넌트 스캔과 자동 의존관계 설정이라고 함.(@Component에 기반해서)
@Controller
public class MemberController {

    private MemberService memberService;
    // 스프링이 딱 뜨자마자 스프링콘테이너 안의 MemberController가 memberService를 찾는다.
    // 그런데 MemberService에는 어떤 어노테이션도 없어서 스프링이 찾을 수가 없다.
    // 그래서 MemberService에 @Service를 붙여줘야 한다.
    // @Controller - @Service - @Repository 정형화된 패턴
    // Controller를 통해서 외부 요청을 받고, Service에서 비즈니스 로직을 만들고, Repository에서 데이터를 저장
    // 생성자에 @Autowired를 쓰면, MemberController-MemberService-MemoryMemberRepository 연결
    // MemberController가 생성이 될 때 스프링 빈에 등록되어 있는 MemberService 객체를 딱 넣어줌
    // 이것이 Dependency Injection - 의존성 주입

    // 생성자 DI 주입 - 이걸 쓰는게 좋음
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 필드 DI주입은 잘 안씀
    // Setter DI 주입
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

}
