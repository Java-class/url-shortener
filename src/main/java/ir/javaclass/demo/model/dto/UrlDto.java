package ir.javaclass.demo.model.dto;

import java.time.LocalDateTime;

public record UrlDto(String originalUrl,
                     String shortUrl,
                     LocalDateTime creationDate) {
}
