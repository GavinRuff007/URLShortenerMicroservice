package org.example.redirectservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.redirectservice.service.RedirectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectService redirectService;

    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = redirectService.getOriginalUrl(shortUrl);

        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }

        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", originalUrl);
    }


}
