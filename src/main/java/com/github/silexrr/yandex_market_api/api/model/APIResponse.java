package com.github.silexrr.yandex_market_api.api.model;

public class APIResponse {
    private APIResponseStatus responseStatus;
    private String requestId;
    private String body;

    public APIResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(APIResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "responseStatus=" + responseStatus +
                ", requestId='" + requestId + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
