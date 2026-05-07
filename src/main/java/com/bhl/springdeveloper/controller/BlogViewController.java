package com.bhl.springdeveloper.controller;

import com.bhl.springdeveloper.dto.ArticleResponse;
import com.bhl.springdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

//    @Autowired
    private final BlogService blogService;

    // 게시글 목록 뷰를 만들어 주는 메서드
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleResponse> articles = blogService.findAll().stream().map(ArticleResponse::new).toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

}
