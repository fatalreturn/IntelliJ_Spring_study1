package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    //MemberRepository repository = new MemoryMemberRepository(); 다향성 -> 인터페이스 repository = new 상속한 클래스
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }
    //콜백 메소드 같은 afterEach 메소드
    //repository에 있는 clearStore 메소드 호출하여 각각 테스트 시 클리어
    //테스틑 서로 의존 관계가 없이 돼야 하기 때문

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //System.out.println("result = " + (result == member));
        //Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);
        //Assertions.assertThat인데 위에 import 해서 assertThat만 사용 가능
        //member와 result가 같은지 테스트
    }
    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //Shit + F6 = 리네임

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
        //result 와 member 1~2가 같은지 테스트
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }
}
