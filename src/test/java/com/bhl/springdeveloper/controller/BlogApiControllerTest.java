package com.bhl.springdeveloper.controller;

import com.bhl.springdeveloper.dao.Article;
import com.bhl.springdeveloper.dto.AddArticleRequest;
import com.bhl.springdeveloper.dto.UpdateArticleRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    protected BlogRepository blogRepository;
    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    public void deleteAll() {
//        blogRepository.deleteAll();
//    }

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
        List<Article> articles = blogRepository.findAll();
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

    @Test
    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공")
    public void findAllArticlesV2() throws Exception {
        // given : 데이터를 하나 삽입
//        blogRepository.save(new Article("title", "content"));
        final String url = "/api/articles";

        blogRepository.save(Article.builder()
                        .title("title")
                        .content("content")
                        .build());

        // when : get 방식으로 /api/articles
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then : status OK이고 읽어온 데이터의 내용이 내가 삽입한 내용과 동일하다
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].content").value("content"));
        /*
         {
            "title" : "title 1",
            "content" : "content 1"
         }
         */
    }

    @DisplayName("findArticle: 블로그 글 조회에 성공한다")
    @Test
    public void findArticle() throws Exception {
        // given (데이터 준비: 블로그글 하나 생성)
        final String url = "/api/articles/{id}";
        final String title = "블로그 제목";
        final String content = "블로그 내용";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when (실행: 위에서 생성된 블로그글을 조회)
        ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then (검증: status가 200이고 조회한 블로그글 제목과 내용이 위에서 삽입한 그것과 동일한지 확인)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content)); // 변환된 JSON 객체의 content 값이 변수 content와 동일하고
                              // 반환된 JSON 객체의 title 값이 변수 title과 동일한지 확인
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "4월 16일";
        final String content = "백엔드프로그래밍(II) 수업";
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId())).andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다")
    @Test
    public void updateArticle() throws Exception {
        // given: 레코드 생성, 변경내용 작성
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        Article savedArticle = blogRepository.save(Article.builder()
                .title(title).content(content).build());

        final String newTitle = "JUnit에서 제목 변경";
        final String newContent = "JUnit에서 내용 변경";
        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when: /api/articles/생성된 레코드 id -> put 방식 요청
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then: status code가 200, repository에서 변경된 내용 검증
        result.andExpect(status().isOk());
        Article article = blogRepository.findById(savedArticle.getId())
                .orElseThrow(() -> new IllegalArgumentException("not found" + savedArticle.getId()));

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }
}