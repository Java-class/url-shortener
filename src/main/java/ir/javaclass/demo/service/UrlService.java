package ir.javaclass.demo.service;

import ir.javaclass.demo.model.dto.UrlDto;
import ir.javaclass.demo.model.dto.UrlMapper;
import ir.javaclass.demo.model.entity.UrlEntity;
import ir.javaclass.demo.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

import static ir.javaclass.demo.service.RandomUtil.generateShortCode;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private final UrlMapper urlMapper;

    @Value("${base.url}")
    private String BASE_URL;

    @Value("${max.length}")
    private int maxLength;

    public UrlDto createShortUrl(String originalUrl) {
        String shortUrl = generateShortCode(maxLength);
        UrlEntity urlEntity = UrlEntity.builder()
                .originalUrl(originalUrl)
                .shortUrl(BASE_URL + shortUrl)
                .build();
        urlEntity = urlRepository.save(urlEntity);
        return urlMapper.toUrlDto(urlEntity);
    }

    public UrlDto getOriginalUrl(String shortUrl) {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByShortUrl(shortUrl);
        if (urlEntityOptional.isPresent()) {
            return urlMapper.toUrlDto(urlEntityOptional.get());
        } else {
            throw new RuntimeException("Data not exist");
        }
    }
}
