package ir.javaclass.demo.controller;

import ir.javaclass.demo.exception.DataNotFoundException;
import ir.javaclass.demo.model.dto.UrlDto;
import ir.javaclass.demo.service.UrlServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UrlController class for define endpoint for manage url services
 *
 * @author Mostafa Anbarmoo
 */

@RestController
@RequestMapping("/api/v1/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlServiceImpl urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlDto> createShortUrl(@Valid @RequestParam String originalUrl) {
        UrlDto urlDto = urlService.createShortUrl(originalUrl);
        return ResponseEntity.ok(urlDto);
    }

    @GetMapping("/original")
    public ResponseEntity<String> getOriginalUrl(@Valid @RequestParam String shortUrl) throws DataNotFoundException {
        var urlDto = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.ok(urlDto.originalUrl());
    }
}
