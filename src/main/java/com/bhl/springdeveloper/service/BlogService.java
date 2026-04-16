package com.bhl.springdeveloper.service;

import com.bhl.springdeveloper.dao.Article;
import com.bhl.springdeveloper.dto.AddArticleRequest;
import com.bhl.springdeveloper.dto.UpdateArticleRequest;
import com.bhl.springdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found:" + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article articles = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
        articles.update(request.getTitle(), request.getContent());
        return articles;
    }
}
