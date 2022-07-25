package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 키값을 생성해줌.

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // 시퀀스 값 하나 올려주기 , 아이디세팅
        store.put(member.getId(), member); // 스토어에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 람다식. 루프로 돌려서 반환.
                .filter(member -> member.getName().equals(name)) // 멤버에서 파라미터로 넘어온 name이 같은지.
                .findAny(); // 하나라도 찾는것.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store에 있는 Member 반환.
    }
    // 여기까지를 검증할 방법은?
    // 검증할 방법은 테스트케이스 작성.

    public void clearStore(){
        store.clear(); // store를 싹 비우는 것.
    }
}
