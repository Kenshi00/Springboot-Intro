package hello.hellospring2.repository;

import hello.hellospring2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// SpringBoot + Jpa로 쓰다가 더 간단히 하는게 SpringDataJpa
// interface가 interface를 받을때는 extends를 씀, 다중상속 가능
// SpringDataJpa가 JpaRepository<>를 상속한 걸 보고
// 자동으로 MemberRepository 구현체(원래는 interface)를 만들어서 빈에 등록해줌
// 애초에 save,findbyid,findall,delete 등 CRUD, 기본적인 기능이 다 구현되어 있음.
// 초반에 만든 repository 함수들도 사실 다 JpaRepository기능을 모티브로 만든 것
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // findByName기능은 Jpa에서 못만들어서 이렇게 따로 써놓은것
    // 하지만 이마저도 구현을 안해도 되는게, 규칙이 있어서 이렇게만 써놔도 됨
    // JPQL select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
