package com.github.silexrr.yandex_market_api.yandexApi;

import com.github.silexrr.yandex_market_api.yandexApi.request.model.Query;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;

public abstract class Method {
    protected Query query;
    protected BodyInserter<Object, ReactiveHttpOutputMessage> body;

    public void setQuery(Query query) {
        this.query = query;
    }
    public Query getQuery() {
        return query;
    }

    public BodyInserter<Object, ReactiveHttpOutputMessage> getBody() {
        return body;
    }

    public void setBody(BodyInserter<Object, ReactiveHttpOutputMessage> body) {
        this.body = body;
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
