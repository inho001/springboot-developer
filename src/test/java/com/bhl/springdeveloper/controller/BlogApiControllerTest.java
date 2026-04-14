package com.bhl.springdeveloper.controller;

import com.bhl.springdeveloper.dto.AddArticleRequest;
import com.bhl.springdeveloper.dao.Article;
import com.bhl.springdeveloper.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper; // 객체를 JSON문자열로 변환

    @Autowired
    protected BlogRepository repository;
    @Autowired
    private BlogRepository blogRepository;

    @Test
    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "테스트";
        final String content = "블로그 첫 번째 글 입니다.";
        final AddArticleRequest article = new AddArticleRequest(title, content);
        final String requestBody = mapper.writeValueAsString(article);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)); // Http Request 보내는 것을 흉내낸다.

        // then
        result.andExpect(status().isCreated());
        List<Article> articles = repository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("findAll() 블로그 글 목록 조회")
    public void findAllArticles() throws Exception {
        blogRepository.save(Article.builder()
                        .title("title 1")
                        .content("content 1")
                        .build());

        blogRepository.save(Article.builder()
                .title("title 2")
                .content("content 2")
                .build());

        blogRepository.save(Article.builder()
                .title("title 3")
                .content("content 3")
                .build());

        ResultActions result = mockMvc.perform(get("/api/articles")
                .accept(MediaType.APPLICATION_JSON_VALUE));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title 1"))
                .andExpect(jsonPath("$[0].content").value("content 1"))
                .andExpect(jsonPath("$[1].title").value("title 2"))
                .andExpect(jsonPath("$[1].content").value("content 2"))
                .andExpect(jsonPath("$[2].title").value("title 3"))
                .andExpect(jsonPath("$[2].content").value("content 3"));
    }
}