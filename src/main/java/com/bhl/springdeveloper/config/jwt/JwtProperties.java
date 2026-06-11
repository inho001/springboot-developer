package com.bhl.springdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // application.yml의 jwt property 값을 가져와서 사용하기 위한 annotation
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
