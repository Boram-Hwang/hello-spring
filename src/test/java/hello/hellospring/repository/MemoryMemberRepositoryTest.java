package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {
// 테스트 케이스 작성
    //테스트 주도 개발(TDD) 알아보기!
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //테스트 끝날때마다 깔끔하게 지워주는 : 메소드 실행이 끝날때마다 콜백메소드라고 보면됨.
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        Assertions.assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result=repository.findByName("spring1").get();

        Assertions.assertThat(result).isEqualTo(member1);
        // findByName이 오류가 나는 이유는? -> findAll 이 먼저 실행되어서 spring1, sping2라는 값이 먼저 저장이 되므로, 이전에 저장된 값이 나와버림.
        // 그래서 테스트가 끝나면 깔끔하게 클리어를 해주어야한다.
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result =repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
