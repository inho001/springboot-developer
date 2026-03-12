package com.bhl.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<Member> getAllMembers() {
        return testRepository.findAll();
    }

    public Member saveMember(Member member) {
        return testRepository.save(member);
    }

}
