package hello.hellospring2.controller;

import hello.hellospring2.domain.Member;
import hello.hellospring2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// @Controller를 붙이면 스프링이 뜰 때 MemberController 객체를 생성해서 스프링 통에 들고 있다.
// 이것을 스프링 컨테이너에서 빈이 관리된다고 표현함.
// @Controller-@Service-@Repository와 같은 방식을 컴포넌트 스캔과 자동 의존관계 설정이라고 함.(@Component에 기반해서)
@Controller
public class MemberController {

    private MemberService memberService;
    // 스프링이 딱 뜨자마자 스프링 콘테이너 안의 MemberController가 memberService를 찾는다.
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

    // home.html에서 /members/new를 회원 가입으로 잡음.
    @GetMapping("/members/new")
    public String createForm(){
        // /members/new로 가면 createMembersForm으로 이동
        return "members/createMembersForm";
    }
    // Post는 form같은 걸로 데이터를 받아서 전달할 때 쓴다.
    // html의 form을 통해 작성한 것은 method="post"로 인해 @PostMapping 있는 곳으로 넘어감
    // Get은 주로 조회할 때 사용
    // <input type="text" id="name" name="name"> 여기서의 name을 보고
    // MemberForm의 private name에 setName을 통해서 넣어준다. (스프링이 알아서)
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; // home 으로 다시 돌아감
    }

    // 회원 목록을 누르면 /members를 찾음.
    // html의 <tr th:each="member:${members}">의 each는 for문 과 같음.
    // 멤버 리스트만큼 표의 행을 완성함.
    @GetMapping("/members")
    public String list(Model model){
        // 전체 회원 list를 받아서 model에 넘긴 후 memberlist html 파일로 return
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
