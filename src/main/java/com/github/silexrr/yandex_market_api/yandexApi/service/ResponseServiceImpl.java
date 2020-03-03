package com.github.silexrr.yandex_market_api.yandexApi.service;

import com.github.silexrr.yandex_market_api.yandexApi.model.Response;
import com.github.silexrr.yandex_market_api.yandexApi.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponseServiceImpl implements ResponseService {
    @Autowired
    private ResponseRepository responseRepository;

    @Override
    public void save(Response response) {
        responseRepository.save(response);
    }

    @Override
    public void delete(Response response) {
        responseRepository.delete(response);
    }

    @Override
    public Optional<Response> findById(String id) {
        return responseRepository.findById(id);
    }
}
