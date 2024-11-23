package ir.javaclass.demo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "url")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "original url is mandatory")
    @Column(name = "original_url", nullable = false)
    private String originalUrl;
    @NotBlank(message = "short url is mandatory")
    @Column(name = "short_url", unique = true, nullable = false)
    private String shortUrl;
    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}
