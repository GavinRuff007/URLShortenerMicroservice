package org.example.redirectservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.redirectservice.service.RedirectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectService redirectService;

    @GetMapping("/{shortUrl}")
    public String redirectPage(@PathVariable String shortUrl, Model model) {
        String originalUrl = redirectService.getOriginalUrl(shortUrl);

        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }

        model.addAttribute("targetUrl", originalUrl);
        return "redirect-page"; // این صفحه Thymeleaf است
    }
}


