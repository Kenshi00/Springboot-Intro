package hello.hellospring2.service;

import hello.hellospring2.domain.Member;
import hello.hellospring2.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // 테스트는 과감하게 한글로 해도 됨. 빌드될 때 테스트 코드는 포함 x

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    // BeforeEach도 AfterEach처럼 각 테스트 실행 전에 실행시켜줌.
    // @BeforeEach -> 각 테스트 실행 전에 호출된다. 테스트가 서로 영향이 없도록 항상 새로운 객체를 생서하고, 의존관계도 새로 맺어준다.
    @BeforeEach
    public void beforeEach(){
        // Service 객체 만들때 생성자를 이용해서 만들기
        // 이와 같이 memberService 입장에선 memberRepository 객체를 외부에서 넣어주는데,
        // 이를 Dependency Ejection, 즉 DI 라고 한다.
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    // 각각의 테스트마다 DB 값을 다 clear 해줌
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {

        // given - 무엇인가 주어졌는데
        Member member = new Member();
        member.setName("spring");

        // when - 이걸 실행했을 때
        Long saveId = memberService.join(member);

        // then - 결과가 이게 나와야 한다.
        // 문장 자동완성 단축키 Ctrl Alt v
        // findOne 반환값이 Optional 이라 get() 사용해서 member변수 추출
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // 테스트는 예외상황을 항상 생각해야함. 중복 회원이 들어오는 경우
    @Test
    public void 중복_회원_예외(){
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        // () -> memberService.join(member2) 이 로직을 실행하면,
        // 왼쪽과 같은 예외가 터져야 한다는 말!
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*
        try{
            memberService.join(member2);
            fail(); // 잘 작동됐으면 catch로 넘어가야 함. fail()까지 오면안됨.
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

        // then

         */
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}