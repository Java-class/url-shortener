# URL SHORTENER

The Spring Boot based Application for manage url shortener services

## Table of Content

- Tech Stack
- Run And Deployment
- API Reference
- Authors
- Social Media Links

## Tech Stack

- Java 21
- Spring Boot
- PostgreSQL
- Redis
- Docker
- Test container
- Maven

## Run And Deployment

For run this project, you must do flowing steps:

1. build the artifact with mvn clean install
2. run with docker-compose up -d

## API Reference

- Request short url

```http
curl --location 'http://localhost:8080/api/v1/url/shorten' \
--form 'originalUrl="https://java-class.ir/about"'

Response sample:
{
    "originalUrl": "https://java-class.ir/about",
    "shortUrl": "http://short.url/6KGEGoGlIC",
    "creationDate": "2024-11-23T22:40:45.975537"
}

```

- Get the original url

```http
  curl --location 'http://localhost:8080/api/v1/url/original?shortUrl=http%3A%2F%2Fshort.url%2FeI2yvtboEe'
```

## Authors

- [@Mostafa Anbarmoo](https://www.github.com/java-class)

## 🔗 Social Media Links

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mostafa-anbarmoo)
