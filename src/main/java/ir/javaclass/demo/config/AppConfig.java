package ir.javaclass.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig class for store and fetch application configuration for services
 *
 * @author Mostafa Anbarmoo
 */

@Configuration
@ConfigurationProperties(prefix = "app.config")
@Getter
@Setter
public class AppConfig {
    private String baseUrl;
    private int maxLength;
    private String randomChars;
}

