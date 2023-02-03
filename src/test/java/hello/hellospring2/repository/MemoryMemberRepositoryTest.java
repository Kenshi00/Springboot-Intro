package hello.hellospring2.repository;

import hello.hellospring2.domain.Member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

// 세모 모양 작품을 만들건데, 만들기 전에 세모 모양 틀을 만들어 놓고,
// 만든 후 끼어 맞추는 것을 TDD, 테스트 주도 개발이라고 한다.
// 여기서는 클래스 먼저 만들고 테스트 했으니 TDD는 아니다.
// Junit5는 여러 Test라이브러리를 제공한다.
// TestCase도 Main의 repository를 테스트한다면 테스트 페키지, 클래스도
// 이름을 똑같이해서 만든다.
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트는 서로 의존관계 없이 설계가 되어야 한다.
    // method가 끝날 때 마다 afterEach 메소드를 실행시켜줌.
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
    // Test 전용으로 실행해줌.
    @Test
    public void save(){
        // 새로 만든거
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // store DB에서 꺼낸거
        // findById 반환타입이 Optional이므로 get()을 사용해서 객체로 꺼냄
        Member result = repository.findById(member.getId()).get();

        // 새로만든거 == store DB에서 꺼낸거 인지 확인해야함
        // System.out.println("result = " + (result == member));
        // 위와 같이 해도 되지만 cmd창을 항상 보는것 보다는 Junit기능을 이용하는게 좋음
        // Junit의 Assertions
        //Assertions.assertEquals(member, result);
        // assertj의 Assertions
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName(){
        // 새로운 멤버1 객체 store에 저장
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // findByName, findById 둘다 반환타입 Optional임 - get() 사용해서 객체 꺼냄
        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

    // 각각 테스트를 돌려보면 오류가 없는데,
    // MemoryMemberRepository 전체로 테스트 케이스를 돌려보면 왜 오류가 나는가
    // findAll, findByName, save 순서는 보장이 안되고
    // findAll이 먼저 실행된다면 spring1, spring2가 만들어진 상태에서 findByName이 실행 될 수 있음.
    // Member result = repository.findByName("spring1").get();
    // 이 문장에서 spring1의 이름을 가진 객체가 이미 존재해서 spring1로 검색하면 서로 다른 객체가 나올 수 있는 것
    // 그래서 동일하다고 보장이 안된다.
    // 그러므로 클래스별로 테스트 실행할 때 마다 데이터 클리어를 해줘야 한다.
}
