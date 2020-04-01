package com.github.silexrr.yandex_market_api.yandexApi;

import com.github.silexrr.yandex_market_api.yandexApi.request.model.Query;

public abstract class Method {
    protected Query query;

    public void setQuery(Query query) {
        this.query = query;
    }
    public Query getQuery() {
        return query;
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
