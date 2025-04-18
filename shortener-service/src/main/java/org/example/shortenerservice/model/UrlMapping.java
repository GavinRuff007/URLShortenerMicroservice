package org.example.shortenerservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Data
@Builder
@Document(collection = "url_mappings")
public class UrlMapping {

    @Id
    private String id;

    @Indexed(unique = true)
    private String shortCode;

    private String originalUrl;

    @Indexed(expireAfterSeconds = 0)
    private Instant expiryDate;

    private Instant createdAt;
}
