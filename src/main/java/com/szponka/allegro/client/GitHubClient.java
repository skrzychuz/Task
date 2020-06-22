package com.szponka.allegro.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GitHubClient {

    private final static Logger logger = LoggerFactory.getLogger(GitHubClient.class);
    private RestTemplate restTemplate;
    @Value("${github_url}")
    private String github_url;

    public GitHubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getRepositoryDetails(String owner, String repositoryName) {

        String url = github_url + owner + "/" + repositoryName;
        logger.info("Requested URL: {}", url);

        try {
            return restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Http Status Code Exception: {}, Message: {}", e.getStatusCode(), e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (RestClientException e) {
            logger.error("Unknown Rest Client Exception: {}", e.getMessage());
            throw new RestClientException("Unknown Rest Client Exception: " + e.getMessage(), e);
        }
    }
}