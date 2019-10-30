package com.github.silexrr.yandex_market_api.shop.model;

public class PriceList {
    private Integer feedId;

    public PriceList() {
    }

    public Integer getFeedId() {
        return feedId;
    }

    public void setFeedId(Integer feedId) {
        this.feedId = feedId;
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "feedId=" + feedId +
                '}';
    }
}
