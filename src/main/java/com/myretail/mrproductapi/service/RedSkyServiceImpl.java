package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedSkyServiceImpl implements RedSkyService {
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(RedSkyServiceImpl.class);

    @Override
    @Retryable(include = {HttpClientErrorException.class, RemoteAccessException.class}, backoff = @Backoff(delay = 300, maxDelay = 500))
    public Optional<RedSkyResponse> getTitle(Integer id) {
        return Optional.ofNullable(restTemplate.getForObject(rsUri(id), RedSkyResponse.class));
    }

    @Recover
    public Optional<?> recover(HttpClientErrorException ex, Integer id) {
        logger.error("Product title not found for product with id: " + id);
        return Optional.empty();
    }

    @Recover
    public Optional<?> recover(RemoteAccessException ex, Integer id) {
        logger.error("Could not reach red sky service to retrieve title for product with id: " + id);
        return Optional.empty();
    }

    private String rsUri(Integer id) {
        return "/v3/pdp/tcin/" + id + "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate";
    }
}
