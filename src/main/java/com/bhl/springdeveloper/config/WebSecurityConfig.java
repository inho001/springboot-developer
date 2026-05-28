package com.bhl.springdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailsService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        /* toH2Console과 /static 폴더 아래의 자바스크립트 요청은 인증하지 않도록 설정 */
        return (web) -> web.ignoring().requestMatchers(toH2Console())
                .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        /*
            /login, /signup, /user
            1. 로그인 페이지, 회원가입 페이지 요청 등은 인증 필터를 거치지 않고 바로 컨트롤러로 전달되도록 설정
            2. 그 외의 모든 요청은 인증을 거치도록 설정
            3. 로그인 폼 페이지 URL 설정
            4. 로그인 성공 시 어느 페이지로 갈지 URL설정 (목록 보기 페이지)
            5. 로그아웃이 설공했을 떄 어느 페이지로 갈지 URL 설정 (로그인 페이지 폼 페이지)
            6. 로그아웃 했을 때 세션 정볼르 무효화 할지 여부를 설정 (true)
         */
        return httpSecurity.authorizeHttpRequests();
    }
}
