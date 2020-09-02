package com.github.silexrr.yandex_market_api.yandexApi.service;

import com.github.silexrr.yandex_market_api.yandexApi.model.Response;

import java.util.List;
import java.util.Optional;

public interface ResponseService {
    void save(Response response);
    void delete(Response response);
    Optional<Response> findById(String id);
    Optional<Response> findByRequestId(String requestId);
    List<Response> findAllByUserId(String userId);
    void deleteAllByDeliveredIsTrue();
}
