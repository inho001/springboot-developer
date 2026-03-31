package com.bhl.springdeveloper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

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
}
