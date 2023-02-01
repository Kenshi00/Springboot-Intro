package hello.hellospring2.repository;

import hello.hellospring2.domain.Member;

import java.util.*;

// MemberRepository 인터페이스를 implements 해서 구현한 것
// MemoryMemberRepository의 기능 - save, findById, findByName, findAll
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // member의 id, name 중 id는 입력하지 않고도 자동으로 등록되게 함
    @Override
    public Member save(Member member) {
        // sequence를 이용해서 id 자동 추가, name은 고객이 알아서
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    // 반환타입이 Optional
    @Override
    public Optional<Member> findById(Long id) {
        // 여기서 get(id)는 map의 내장함수다. id값으로 store에 있는 map(id, member) 반환
        // Optional.ofNullable은 null인 경우를 대비하기 위함
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // stream.filter() - 인자로 함수를 받는다.
        // store.values() - {1,jiyun}, {2, nami}.. filter로 순회하면서
        // findAny()로 파라미터 name값이랑 같은거 암거나 발견하면 반환
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // member들을 ArrayList로 반환
        return new ArrayList<>(store.values());
    }

    // 테스트할 때 afterEach를 위해서 만듬. store를 싹 비워준다.
    public void clearStore(){
        store.clear();
    }
}
