package com.bhl.springdeveloper.controller;

import com.bhl.springdeveloper.dao.Article;
import com.bhl.springdeveloper.dto.ArticleResponse;
import com.bhl.springdeveloper.dto.ArticleViewResponse;
import com.bhl.springdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

//    @Autowired
    private final BlogService blogService;

    // 게시글 목록 뷰를 만들어 주는 메서드
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleResponse> articles =
                blogService.findAll().stream().map(ArticleResponse::new).toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

}
