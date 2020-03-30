package com.github.silexrr.yandex_market_api.yandexApi;

import com.github.silexrr.yandex_market_api.yandexApi.request.model.Request;

public abstract class Method {
    protected Request request;

    public void setRequest(Request request) {
        this.request = request;
    }
    public Request getRequest() {
        return request;
    }

    public final String getMethodName() {
        Class<? extends Method> aClass = this.getClass();
        String packageName = aClass.getPackage().getName();
        String superPackageName = aClass.getSuperclass().getPackage().getName();
        packageName = packageName.replace(superPackageName + ".method.", "");

        return packageName + "." + aClass.getSimpleName();
    }

    public abstract void execute();
}
