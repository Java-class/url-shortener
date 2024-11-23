package ir.javaclass.demo.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UrlDto(String originalUrl,
                     String shortUrl,
                     LocalDateTime creationDate) implements Serializable {
}
