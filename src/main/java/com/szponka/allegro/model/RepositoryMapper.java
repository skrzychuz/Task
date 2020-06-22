package com.szponka.allegro.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RepositoryMapper {

    private final static Logger logger = LoggerFactory.getLogger(RepositoryMapper.class);
    private final ObjectMapper mapper;

    public RepositoryMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public RepositoryDetails mapJsonBodyToRepositoryDetails(ResponseEntity<String> entity) {

        return Optional.ofNullable(entity.getBody())
                .map(body -> {
                    try {
                        return mapper.readValue(body, RepositoryDetails.class);
                    } catch (JsonProcessingException e) {
                        logger.error("Error during JSON processing: {}", e.getMessage());
                        throw new IllegalArgumentException("Error during JSON processing", e);
                    }
                }).orElseThrow(() -> new RuntimeException("No body content in ResponseEntity to map"));
    }
}
