package org.example.apigateway.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/shortener")
    public ResponseEntity<String> shortenerFallback() {
        return ResponseEntity.ok("Shortener Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/redirect")
    public ResponseEntity<String> redirectFallback() {
        return ResponseEntity.ok("Redirect Service is currently unavailable. Please try again later.");
    }
}
