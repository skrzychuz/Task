package com.szponka.allegro.service;

import com.szponka.allegro.model.RepositoryDetails;
import com.szponka.allegro.model.RepositoryMapper;
import com.szponka.allegro.client.GitHubClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GitHubService {

    private final GitHubClient gitHubClient;
    private final RepositoryMapper repositoryMapper;

    public GitHubService(GitHubClient gitHubClient, RepositoryMapper repositoryMapper) {
        this.gitHubClient = gitHubClient;
        this.repositoryMapper = repositoryMapper;
    }

    public RepositoryDetails getGitHubRepositoryDetails(String owner, String name) {
        ResponseEntity<String> entityResponse = gitHubClient.getRepositoryDetails(owner, name);
        return repositoryMapper.mapJsonBodyToRepositoryDetails(entityResponse);
    }
}
