package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ShopValidator implements Validator {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return Shop.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Shop.name.required");
        ValidationUtils.rejectIfEmpty(errors, "ymLogin", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "ymCompanyId", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "userOwners", "Shop.error.needAuthentication");

        Shop shop = (Shop) o;
        Optional<Shop> shopOptionalByName = this.shopRepository.findByName(shop.getName());
        if (shopOptionalByName.isPresent()
            && !shopOptionalByName.get().getId().equals(shop.getId())
        ) {
            errors.rejectValue(
                "name",
                "Shop.name.taken"
            );
        }
        Integer ymCompanyId = shop.getYmCompanyId();
        if (ymCompanyId == null
            || ymCompanyId == 0
        ) {
            errors.rejectValue(
                    "ymCompanyId",
                    "NotEmpty"
            );
        } else {
            Optional<Shop> shopOptionalByYmCompanyId = this.shopRepository.findByYmCompanyId(ymCompanyId);
            if (shopOptionalByYmCompanyId.isPresent()
                && !shopOptionalByYmCompanyId.get().getId().equals(shop.getId())
            ) {
                errors.rejectValue(
                        "ymCompanyId",
                        "Shop.ymCompanyId.taken"
                );
            }
        }
    }
}
