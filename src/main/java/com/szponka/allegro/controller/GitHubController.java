package com.szponka.allegro.controller;

import com.szponka.allegro.model.RepositoryDetails;
import com.szponka.allegro.service.GitHubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubController {

    private final GitHubService gitHubService;
    private final static Logger logger = LoggerFactory.getLogger(GitHubController.class);

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/repositories/{owner}/{repositoryName}")
    public RepositoryDetails getRepositoryDetails(@PathVariable String owner, @PathVariable String repositoryName) {
        logger.info("Requested owner: {}, Repository name: {}", owner, repositoryName);
        return gitHubService.getGitHubRepositoryDetails(owner, repositoryName);
    }
}
