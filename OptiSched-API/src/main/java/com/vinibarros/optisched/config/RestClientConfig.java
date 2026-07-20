package com.vinibarros.optisched.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${optimizer.url}")
    private String optimizerBaseUrl;

    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory requestFactory =  new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(120000);

        return RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(optimizerBaseUrl)
                .build();
    }
}
