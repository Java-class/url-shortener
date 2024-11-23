package ir.javaclass.demo.service;

import ir.javaclass.demo.config.AppConfig;
import ir.javaclass.demo.exception.DataNotFoundException;
import ir.javaclass.demo.model.dto.UrlDto;
import ir.javaclass.demo.model.dto.UrlMapper;
import ir.javaclass.demo.model.entity.UrlEntity;
import ir.javaclass.demo.repository.UrlRepository;
import ir.javaclass.demo.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final AppConfig appConfig;
    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;

    @Override
    public UrlDto createShortUrl(String originalUrl) {
        UrlEntity urlEntity;
        Optional<UrlEntity> history = urlRepository.findByOriginalUrl(originalUrl);
        if (history.isPresent()) {
            urlEntity = history.get();
        } else {
            String shortUrl = RandomUtil.generateShortCode(appConfig.getMaxLength(), appConfig.getRandomChars());
            urlEntity = UrlEntity.builder()
                    .originalUrl(originalUrl)
                    .shortUrl(appConfig.getBaseUrl() + shortUrl)
                    .build();
            urlEntity = urlRepository.save(urlEntity);
        }
        return urlMapper.toUrlDto(urlEntity);
    }


    @Override
    @Cacheable(value = "url", key = "#shortUrl")
    public UrlDto getOriginalUrl(String shortUrl) throws DataNotFoundException {
        return urlRepository.findByShortUrl(appConfig.getBaseUrl() + shortUrl)
                .map(urlMapper::toUrlDto)
                .orElseThrow(DataNotFoundException::new);
    }
}
