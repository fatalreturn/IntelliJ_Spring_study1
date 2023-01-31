package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    //contructor

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }//디펜던시 인젝션(DI)

    /**
     *
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름의 중복 회원x
        long start = System.currentTimeMillis();


        //Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(member -> {//if null이 아니면
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }); 아래와 같이 줄여서 하기
        //result.orElseGet(); 얘는 별도로 orElseGet의 사용법 -> 값이 널이면 어떠한 메소드 실행 등
        try {
            validateDuplicateMember(member); // 중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        long start = System.currentTimeMillis();
        try{
            return memberRepository.findAll();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = " + timeMs + "ms");
        }


    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
