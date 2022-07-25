package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    // MemberService에 있는 memberRepository는 new로 만들어진 다른 객체입니다. 지금은 static이 선언되어 있고, 코드가 짧아 가능
    // 또한, MemberServiceTest에 있는 memberRepository는 Service에 있는 repository랑 다른 객체입니다.
    // 두 개를 쓸 이유도 없고, 같은 걸 쓰느게 좋다. 다르게 쓸경우 혹시라도 다른 인스턴스이기 때문에 내용물이 달라질 수 잇다.

    @BeforeEach
    public void beforeEach(){ // 동작하기 전에 넣어주기
        memberRepository = new MemoryMemberRepository(); // memberRepository만들어서 memberRepository에 넣기
        memberService = new MemberService(memberRepository); // memberService 클래스의 memberRepository에 넣어줘서 같은 MemoryMemberRepository를 사용
        // 이것을 memberService 입장에서는 내가 직접 new 하지 않고 외부에서 memberRepository 넣어준다. 이것을 DI(Dependemcy Insection?)라고 한다.
    }

    //테스트 끝날때마다 깔끔하게 지워주는 : 메소드 실행이 끝날때마다 콜백메소드라고 보면됨.
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }


    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId=memberService.join(member); // join메소드의 return은 저장한Id가 튀어나오기로했음.

        // then (검증)
        Member findMember =memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // 테스트는 예외상황이 가장 중요해서 예외를 한번 터트려보는것도 중요하다.
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        // 예외를 잡는 좋은 방법 assertThrows
        // assertThrows(IllegalStateException.class, () -> memberService.join(member2)); //
        // * 반환 단축키는 = Ctrl + Alt + V
        // 밑에는 메세지를 검증하는 방법
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // 예외를 잡는 방법 try ~ catch
/*        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/


        //then
    }



    @Test
    void findMembeers() {
    }

    @Test
    void findOne() {
    }
}