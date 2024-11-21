package ir.javaclass.demo.model.dto;

import ir.javaclass.demo.model.entity.UrlEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlMapper {
    UrlDto toUrlDto(UrlEntity urlEntity);
}
