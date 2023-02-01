package hello.hellospring2.repository;

import hello.hellospring2.domain.Member;

import java.util.List;
import java.util.Optional;

// 데이터 저장소가 선정되지 않은 상태라 가정,
// 그래서 우선 인터페이스로 구현 클래스를 변경할 수 있도록 설계
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id); // Optional은 Java8 에 있는 내용, null을 감싸주는 역할
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
