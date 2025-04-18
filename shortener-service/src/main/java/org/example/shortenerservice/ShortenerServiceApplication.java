package org.example.shortenerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class ShortenerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortenerServiceApplication.class, args);
    }

}
