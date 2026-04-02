package com.bhl.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // @Transactional 포함
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Sql("/insert-members.sql")
    void getAllMembers() {
        // given(준비)

        // when(실행)
        List<Member> members = memberRepository.findAll();// select * from member;

        // then(검증)
        assertThat(members.size()).isEqualTo(3);
    }

    @Test
    @Sql("/insert-members.sql")
    void getMemberById() {
        // given

        // when
        Member member = memberRepository.findById(2L).get();

        // then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Test
    @Sql("/insert-members.sql")
    void getMemberByName() {
        // given

        // when
        // select * from member where name=:name
        Member member = memberRepository.findByName("C").get();

        // then
        assertThat(member.getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("레코드 삽입 테스트")
    void saveMember() {
        // given
        Member m = new Member(1L, "scpark");

        // when
        Member savedMember = memberRepository.save(m);

        // 1. Member 객체 m에 primary key인 id가 없으면 :
        //      insert into member(name) values('scpark')
        // 2. Member 객체 m에 primary key가 이미 설정되어 있으면 :
        //      update member set name = 'scpark' where id = 1
        // save 메서드가 성공하면 삽입된 또는 update된 레코드를 Member 객체로 반환
        // 3. return new Member(부여된 id, "scpark");

        // then
        // Optional<Member>
        assertThat(savedMember.getId()).isNotNull(); // 삽입에 성공했는지 체크
        // MemberRepository의 findById() 메서드는
        // 1. select * from member where id = :id
        // 2. return new Optional<Member>(1L, "scpark")
        Long id = savedMember.getId();
        Optional<Member> result = memberRepository.findById(id);
        Member member = result.get();
        String name = member.getName();

        assertThat(name).isEqualTo("scpark");

//        assertThat(memberRepository.findById(savedMember.getId()).get().getName()).isEqualTo("scpark");
    }

    @DisplayName("2개의 레코드를 한 번에 삽입하는 테스트")
    @Test
    void saveMembers() {
        // given
        List<Member> members = List.of(new Member("HongGilDong"), new Member("ParkMunSu"));

        // when
        memberRepository.saveAll(members);

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @Test
    @DisplayName("Record 삭제 테스트")
    void deleteAll() {
        // given

        // when
        memberRepository.deleteAll();

        // then
        int memberListSize = memberRepository.findAll().size();
        assertThat(memberListSize).isZero();
    }

    @Sql("/insert-members.sql")
    @Test
    @DisplayName("update Test")
    void update() {
        // given
        Member member = memberRepository.findById(2L).get();
        // when
        member.changeName("scpark");
        // then
        String memberName = memberRepository.findById(2L).get().getName();
        assertThat(memberName).isEqualTo("scpark");
    }
}
