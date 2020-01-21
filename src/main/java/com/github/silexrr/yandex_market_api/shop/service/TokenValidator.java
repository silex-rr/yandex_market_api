package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Token;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TokenValidator implements Validator {

//    @Autowired
//    private TokenRepository tokenRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return Token.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oauthToken", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oauthClientId", "NotEmpty");

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
