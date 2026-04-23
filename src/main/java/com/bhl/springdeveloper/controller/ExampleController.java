package com.bhl.springdeveloper.controller;

import com.bhl.springdeveloper.dao.Article;
import com.bhl.springdeveloper.dto.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ExampleController {

    @GetMapping("/example")
    public String example(Model model) {
        List<ArticleResponse> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            ArticleResponse a = new ArticleResponse(new Article("제목" + i, i + "번째 내용"));
            list.add(a);
        }
        model.addAttribute("articles", list);
        model.addAttribute("name", "홍길동");
//        model.addAttribute("error", "검색 오류발생");

        Person p = new Person(1L, "홍길동", 18, List.of("운동", "독서", "영화", "음악", "등산"));
        model.addAttribute("person", p);

        return "example"; // src/main/resources/templates/example.html
    }
}

@Setter
@Getter
@AllArgsConstructor
class Person {
    private Long id;
    private String name;
    private int age;
    private List<String> hobbies;
}