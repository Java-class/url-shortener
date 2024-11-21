package ir.javaclass.demo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "original url is mandatory")
    private String originalUrl;
    @NotBlank(message = "short url is mandatory")
    @Column(unique = true)
    private String shortUrl;
    @CreationTimestamp
    private LocalDateTime creationDate;
}
