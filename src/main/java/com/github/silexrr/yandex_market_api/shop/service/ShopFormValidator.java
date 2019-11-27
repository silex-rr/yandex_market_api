package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ShopFormValidator implements Validator {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return Shop.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ymLogin", "ymLogin.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ymCompanyId", "ymCompanyId.required");

        Shop shop = (Shop) o;
        Shop shopInDbByName = this.shopRepository.findByName(shop.getName());
        if (shopInDbByName != null) {
            errors.rejectValue(
                "name",
                "nameTaken",
                    new Object[]{"'name'"},
                "This shop name is already taken"
            );
        }
        Shop shopInDbByYmCompanyId = this.shopRepository.findByYmCompanyId(shop.getYmCompanyId());
        if (shopInDbByYmCompanyId != null) {
            errors.rejectValue(
                "ymCompanyId",
                "ymCompanyIdTaken",
                    new Object[]{"'ymCompanyId'"},
                "This YM company ID is already taken"
            );
        }
    }
}
