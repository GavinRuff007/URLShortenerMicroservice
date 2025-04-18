package org.example.shortenerservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.shortenerservice.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shorten")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService service;

    @PostMapping
    public String shortenUrl(@RequestBody String originalUrl) {
        return service.shortenUrl(originalUrl);
    }
}
