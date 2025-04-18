package org.example.shortenerservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.shortenerservice.dto.ShortenUrlRequest;
import org.example.shortenerservice.dto.ShortenUrlResponse;
import org.example.shortenerservice.service.UrlShortenerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shorten")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService service;

    @PostMapping
    public ShortenUrlResponse shortenUrl(@RequestBody ShortenUrlRequest originalUrl) {
        String shortUrl = service.shortenUrl(originalUrl.getLongUrl());
        ShortenUrlResponse response = new ShortenUrlResponse();
        response.setShortUrl(shortUrl);
        return response;
    }
}
