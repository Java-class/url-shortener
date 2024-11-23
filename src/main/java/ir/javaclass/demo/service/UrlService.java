package ir.javaclass.demo.service;

import ir.javaclass.demo.exception.DataNotFoundException;
import ir.javaclass.demo.model.dto.UrlDto;

/**
 * UrlService Interface for manage create and retrieve urls
 *
 * @author Mostafa Anbarmoo
 */
public interface UrlService {

    UrlDto createShortUrl(String originalUrl);

    UrlDto getOriginalUrl(String shortUrl) throws DataNotFoundException;
}
