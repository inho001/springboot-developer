package com.bhl.springdeveloper.controller;

import com.bhl.springdeveloper.dto.AddArticleRequest;
import com.bhl.springdeveloper.dao.Article;
import com.bhl.springdeveloper.dto.ArticleResponse;
import com.bhl.springdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<Article> articles = blogService.findAll();
        List<ArticleResponse> result = articles.stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(result);
    }

}
