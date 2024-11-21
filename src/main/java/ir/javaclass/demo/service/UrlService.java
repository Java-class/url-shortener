package ir.javaclass.demo.service;

import ir.javaclass.demo.config.AppConfig;
import ir.javaclass.demo.exception.DataNotFoundException;
import ir.javaclass.demo.model.dto.UrlDto;
import ir.javaclass.demo.model.dto.UrlMapper;
import ir.javaclass.demo.model.entity.UrlEntity;
import ir.javaclass.demo.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UrlService {
    private final AppConfig appConfig;
    private final UrlRepository urlRepository;
    private final RandomGenerator randomGenerator;
    private final UrlMapper urlMapper;

    public UrlDto createShortUrl(String originalUrl) {
        String shortUrl = randomGenerator.generateShortCode(appConfig.getMaxLength());
        UrlEntity urlEntity = UrlEntity.builder()
                .originalUrl(originalUrl)
                .shortUrl(appConfig.getBaseUrl() + shortUrl)
                .build();
        urlEntity = urlRepository.save(urlEntity);
        return urlMapper.toUrlDto(urlEntity);
    }

    public UrlDto getOriginalUrl(String shortUrl) throws DataNotFoundException {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByShortUrl(shortUrl);
        if (urlEntityOptional.isPresent()) {
            return urlMapper.toUrlDto(urlEntityOptional.get());
        } else {
            throw new DataNotFoundException();
        }
    }
}
