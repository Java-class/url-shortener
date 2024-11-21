package ir.javaclass.demo.controller;

import ir.javaclass.demo.exception.DataNotFoundException;
import ir.javaclass.demo.model.dto.UrlDto;
import ir.javaclass.demo.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlDto> createShortUrl(@RequestParam String originalUrl) {
        UrlDto urlDto = urlService.createShortUrl(originalUrl);
        return ResponseEntity.ok(urlDto);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) throws DataNotFoundException {
        var urlDto = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.ok(urlDto.originalUrl());
    }
}
