package hello.hellospring2.repository;

import hello.hellospring2.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class JpaMemberRepository implements MemberRepository{

    // jpa는 EntityManager로 모든게 동작한다.
    // build.gradle로 data-jpa라이브러리 받았으면 스프링부트가 자동으로 entitymanager 생성
    // 데이터베이스 연결된 entitymanager 알아서 만들어줌
    private final EntityManager em;
    // 결론적으로, jpa를 사용하기 위해서는 EntityManager를 주입 받아야 한다~
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public Member save(Member member) {
        // 사실상 이게 끝 - jpa가 insert 쿼리 만들어서 db에 집어 넣음
        // jpa가 알아서 setId 까지 다 해줌
        em.persist(member);
        return member; // 이 return은 그냥 형식상 맞추기 위한 것
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 객체 종류랑 id만 알면 jpa에서 알아서 select문 완성
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // jpql 이라는 jpa전용 sql문
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}
