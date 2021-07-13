package com.example.demo.usecase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
public class SampleUseCase {

    private final RestTemplate restTemplate = new RestTemplate();

    @Retryable(value = {HttpClientErrorException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public void hello() {
        log.info(">> SampleUseCase.hello()");
        try {
            URI uri = null;
            try {
                uri = new URI("https://www.google.com/sonzai-shinai");
            } catch (URISyntaxException e) {
                log.error("URISyntaxException", e);
            }
            RequestEntity req = RequestEntity.get(uri).build();
            restTemplate.exchange(req, String.class);
        } finally {
            log.info("<< SampleUseCase.hello()");
        }
    }

    // retry処理に全て失敗した時の処理
    @Recover
    private void recoverHello(HttpClientErrorException e) {
        log.error("HttpClientErrorException", e);
    }
}
