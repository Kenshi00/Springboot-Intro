package hello.hellospring2.service;

import hello.hellospring2.domain.Member;
import hello.hellospring2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// MemberService나 MemoryMemberRepository나 비슷하다고 느낄 수 있지만,
// Service가 join, findmembers 등 비즈니스적인 기능이라고 볼 수 있다.
// MemberService의 기능 - join, findMembers, findOne

// Ctrl + Shift + T -> test 쉽게 만들어줌

// MemberController에는 @Controller, MemberService에는 @Service
// MemberRepository에는 @Repository
// @Service

// jpa를 사용하기 위해서는 항상 Transactional이 있어야함
@Transactional
public class MemberService {

    // MemberRepository는 MemoryMemberRepository의 인터페이스
    // memberRepository라는 이름으로 new MemoryMemberRepository 객체 생성
    private final MemberRepository memberRepository;
    // Service와 ServiceTest에서 서로 다른 memberRepository 객체를 사용하면
    // 테스트한 결과가 신빙성이 덜하다. (예상치 못한 오류 발생 가능).
    // 그래서 MemberService 객체를 생성할 때, memberRepository 객체를 외부에서 넣어주도록 생성자를 만들어준다.
    // Autowired가 붙으면 MemberService를 생성을 할 때 스프링 컨테이너에 있는 memberRepository를 넣어줌
    // @Autowired

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member){

            // 같은 이름이 있는 중복 회원x 기능
            // 파라미터 member의 이름을 검색한 결과가 null이 아니면 (present하면) 예외 발생
            validateDuplicateMember(member); // 중복 회원 검증
            memberRepository.save(member); // Map<Long, Member> store DB에 저장됨.
            return member.getId(); // id 추출
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * Id로 회원 찾기
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
