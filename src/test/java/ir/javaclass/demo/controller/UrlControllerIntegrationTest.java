package ir.javaclass.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.javaclass.demo.UrlShortenerApplication;
import ir.javaclass.demo.model.dto.UrlDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = UrlShortenerApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UrlControllerIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpassword");
    @Container
    private static final GenericContainer<?> redisContainer =
            new GenericContainer<>("redis:7-alpine").withExposedPorts(6379);
    private final String base_url = "http://short.url/";
    private final String originalUrl = "https://java-class.ir/about";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        postgresContainer.start();
        redisContainer.start();
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }


    @Test
    void testCreateShortUrl_success() throws Exception {
        mockMvc.perform(post("/api/v1/url/shorten")
                        .param("originalUrl", originalUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value(originalUrl))
                .andExpect(jsonPath("$.shortUrl").exists());
    }

    @Test
    void testCreateShortUrl_invalidRequest() throws Exception {
        mockMvc.perform(post("/api/v1/url/shorten")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetOriginalUrl_success() throws Exception {
        // First, create a short URL
        var result = mockMvc.perform(post("/api/v1/url/shorten")
                        .param("originalUrl", originalUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UrlDto urlDto = objectMapper.readValue(result.getResponse().getContentAsString(), UrlDto.class);

        // Then, fetch the original URL using the generated short URL
        mockMvc.perform(get("/api/v1/url/original")
                        .param("shortUrl", urlDto.shortUrl().replace(base_url, ""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(originalUrl));
    }

    @Test
    void testGetOriginalUrl_notFound() throws Exception {
        mockMvc.perform(get("/api/v1/url/original")
                        .param("shortUrl", "notFoundUrl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMovedPermanently());
    }

    @Test
    void testGetOriginalUrl_invalidRequest() throws Exception {
        mockMvc.perform(get("/api/v1/url/original")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @AfterAll
    public void cleanUp() {
        postgresContainer.stop();
        redisContainer.stop();
    }
}
