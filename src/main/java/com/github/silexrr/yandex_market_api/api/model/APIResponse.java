package com.github.silexrr.yandex_market_api.api.model;

public class APIResponse {
    private APIResponseStatus responseStatus;
    private String requestId;
    private long messageCount;
    private long timeToReady;
    private String body;

    public APIResponse() {
        messageCount = 0;
        timeToReady = 0;
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

    public long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }

    public long getTimeToReady() {
        return timeToReady;
    }

    public void setTimeToReady(long timeToReady) {
        this.timeToReady = timeToReady;
    }


    @Override
    public String toString() {
        return "APIResponse{" +
                "responseStatus=" + responseStatus +
                ", requestId='" + requestId + '\'' +
                ", queueNumber=" + messageCount +
                ", secondsLeftUntilReady=" + timeToReady +
                ", body='" + body + '\'' +
                '}';
    }
}
