package hello.hellospring2.service;

import hello.hellospring2.domain.Member;
import hello.hellospring2.repository.MemberRepository;
import hello.hellospring2.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// @BeforeEach, @AfterEach 대신 해주는 역할이 @Transactional이라 생각하면 됨.
// @Transactional을 달면, 테스트 시작 전에 트랜잭션을 실행하고, insert한 DB 데이터들이 다시 롤백됨. (테스트 결과 실제 반영안됨)
@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional
class MemberServiceIntegrationTest {
    // 테스트는 과감하게 한글로 해도 됨. 빌드될 때 테스트 코드는 포함 x

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // @Commit 하면 DB에 롤백 무시하고 저장됨
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

    }

}