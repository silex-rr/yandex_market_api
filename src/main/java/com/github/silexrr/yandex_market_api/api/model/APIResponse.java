package com.github.silexrr.yandex_market_api.api.model;

public class APIResponse {
    private APIResponseStatus responseStatus;
    private String requestId;
    private long queueNumber;
    private long secondsLeftUntilReady;
    private String body;

    public APIResponse() {
        queueNumber = 0;
        secondsLeftUntilReady = 0;
    }

    public APIResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(APIResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
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

    public long getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(long queueNumber) {
        this.queueNumber = queueNumber;
    }

    public long getSecondsLeftUntilReady() {
        return secondsLeftUntilReady;
    }

    public void setSecondsLeftUntilReady(long secondsLeftUntilReady) {
        this.secondsLeftUntilReady = secondsLeftUntilReady;
    }


    @Override
    public String toString() {
        return "APIResponse{" +
                "responseStatus=" + responseStatus +
                ", requestId='" + requestId + '\'' +
                ", queueNumber=" + queueNumber +
                ", secondsLeftUntilReady=" + secondsLeftUntilReady +
                ", body='" + body + '\'' +
                '}';
    }
}
