package ir.javaclass.demo.repository;

import ir.javaclass.demo.model.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UrlRepository class for manage create and retrieve url entity
 *
 * @author Mostafa Anbarmoo
 */

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByOriginalUrl(String originalUrl);

    Optional<UrlEntity> findByShortUrl(String shortUrl);
}

