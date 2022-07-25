package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    // 회원리포지토리 생성
    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 같은 memberRepository의 객체로 만들고 싶다면 new 된걸 지우고, constructor 단축키 사용 : (커서놓구)Alt + Enter
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; // memberRepository를 외부에서 넣어주도록 (직접생성X)
    }


    //회원 가입
    public Long join(Member member){

        //Optional<Member> result = memberRepository.findByName(member.getName()); // 코드가 이쁘지 않기 때문에 어차피 Optional이 반환이 되어서
        //Member member1 = result.get(); // 그냥 꺼내고 싶으면 get() 으로 꺼내면 된다. 권장 X
//        memberRepository.findByName(member.getName()).ifPresent(m -> { // ifPresent는 null이 아닌 어떤 값이 있으면 동작 .. Optional이기 때문에 가능
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });
        // Refactor This의 Move Instance Method...를 사용하려면
        // 단축키 Ctrl + Shift + Alt + T
        // 같은 이름이 있는 중복 회원 X
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> { // ifPresent는 null이 아닌 어떤 값이 있으면 동작 .. Optional이기 때문에 가능
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    // 전체 회원 조회
    public List<Member> findMembeers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
