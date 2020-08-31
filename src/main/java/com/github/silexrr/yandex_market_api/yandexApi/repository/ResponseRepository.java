package com.github.silexrr.yandex_market_api.yandexApi.repository;

import com.github.silexrr.yandex_market_api.yandexApi.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends MongoRepository<Response, String> {
    @Override
    public Optional<Response> findById(String s);
    public Optional<Response> findByRequestId(String requestId);
    public List<Response> findAllByUserId(String userId);
}
