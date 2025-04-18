package org.example.shortenerservice.dto;

import lombok.Data;

@Data
public class ShortenUrlResponse {

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    private String shortUrl;
}
