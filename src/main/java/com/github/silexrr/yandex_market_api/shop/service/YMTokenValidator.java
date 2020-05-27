package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.YMToken;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class YMTokenValidator implements Validator {

//    @Autowired
//    private TokenRepository tokenRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return YMToken.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oauthToken", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oauthClientId", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

//        Token token = (Token) o;
//
//
//
//        Token tokenByOauthToken = tokenRepository.findByOauthToken(token.getOauthToken());
//        if (tokenByOauthToken != null
//            && !tokenByOauthToken.getId().equals(token.getId())
//        ) {
//            errors.rejectValue("oauthToken", "Shop.Token.oauthToken.taken");
//        }
    }
}
