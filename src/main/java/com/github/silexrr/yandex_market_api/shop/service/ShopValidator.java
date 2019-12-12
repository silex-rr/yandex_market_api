package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
        ValidationUtils.rejectIfEmpty(errors, "userOwner", "Shop.error.needAuthentication");

        Shop shop = (Shop) o;
        Shop shopInDbByName = this.shopRepository.findByName(shop.getName());
        if (shopInDbByName != null) {
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
            Shop shopInDbByYmCompanyId = this.shopRepository.findByYmCompanyId(ymCompanyId);
            if (shopInDbByYmCompanyId != null) {
                errors.rejectValue(
                        "ymCompanyId",
                        "Shop.ymCompanyId.taken"
                );
            }
        }
    }
}
