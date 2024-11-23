package ir.javaclass.demo.service;

import ir.javaclass.demo.config.AppConfig;
import ir.javaclass.demo.exception.DataNotFoundException;
import ir.javaclass.demo.model.dto.UrlDto;
import ir.javaclass.demo.model.dto.UrlMapper;
import ir.javaclass.demo.model.entity.UrlEntity;
import ir.javaclass.demo.repository.UrlRepository;
import ir.javaclass.demo.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UrlService class for manage create and retrieve urls
 *
 * @author Mostafa Anbarmoo
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {
    private final AppConfig appConfig;
    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;

    @Override
    public UrlDto createShortUrl(String originalUrl) {
        log.info("try to create new short url for original url: {}", originalUrl);
        UrlEntity urlEntity;
        Optional<UrlEntity> history = urlRepository.findByOriginalUrl(originalUrl);
        if (history.isPresent()) {
            log.info("short url already exist for original url: {}", originalUrl);
            urlEntity = history.get();
        } else {
            String shortUrl = RandomUtil.generateShortCode(appConfig.getMaxLength(), appConfig.getRandomChars());
            urlEntity = UrlEntity.builder()
                    .originalUrl(originalUrl)
                    .shortUrl(appConfig.getBaseUrl() + shortUrl)
                    .build();
            urlEntity = urlRepository.save(urlEntity);
            log.info("short url successfully created for original url: {}", originalUrl);
        }
        return urlMapper.toUrlDto(urlEntity);
    }

    @Override
    @Cacheable(value = "url", key = "#shortUrl")
    public UrlDto getOriginalUrl(String shortUrl) throws DataNotFoundException {
        shortUrl = shortUrl.replace(appConfig.getBaseUrl(), "");
        return urlRepository.findByShortUrl(appConfig.getBaseUrl() + shortUrl)
                .map(urlMapper::toUrlDto)
                .orElseThrow(DataNotFoundException::new);
    }
}
