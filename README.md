# URL SHORTENER

The Spring Boot based Application for manage url shortener services

## Table of Content

- Tech Stack
- Run And Deployment
- API Reference
- Future Improvement
- Authors
- Social Media Links

## Tech Stack

- Java 21
- Spring Boot 3.3.6
- Maven

## Run And Deployment

For run this project, you must do flowing steps:

1. build the artifact with mvn clean install
2. run with docker-compose up -d
3. Add Log4j for logging system and user behavior

## API Reference

- Request short url

```http
curl --location 'http://localhost:8080/api/v1/url/shorten' \
--form 'originalUrl="https://java-class.ir/about"'
```

- Get original url

```http
  curl --location 'http://localhost:8080/api/v1/url/original?shortUrl=http%3A%2F%2Fshort.url%2FeI2yvtboEe'
```

## Future Improvement

1. Improve random generator algorithm
2. Add expiration time for data
3. Add authentication and authorization for users

## Authors

- [@Mostafa Anbarmoo](https://www.github.com/java-class)

## 🔗 Social Media Links

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mostafa-anbarmoo)
