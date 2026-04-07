package com.bhl.springdeveloper.service;

import com.bhl.springdeveloper.dao.AddArticleRequest;
import com.bhl.springdeveloper.dao.Article;
import com.bhl.springdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}
