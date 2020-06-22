package com.szponka.allegro;

import com.szponka.allegro.client.GitHubClient;
import com.szponka.allegro.controller.GitHubController;
import com.szponka.allegro.model.RepositoryDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(locations = "classpath:mock.properties", properties = {"github_url=http://localhost:${wiremock.server.port}/"})
public class AllegroApplicationMockTest {

    @Autowired
    private GitHubController controller;
    @Autowired
    private GitHubClient githubClient;
    @Value("${test_owner}")
    private String owner;
    @Value("${test_name}")
    private String repositoryName;
    @Value("${test_url}")
    private String url;
    @Value("${test_status}")
    private int statusCode;
    @Value("${test_file}")
    private String filePath;
    @Value("${test_full_name}")
    private String fullName;
    @Value("${test_description}")
    private String description;
    @Value("${test_clone_url}")
    private String cloneUrl;
    @Value("${test_created_at}")
    private String createdAt;
    @Value("${test_stars}")
    int stars;

    @Test
    public void verifyAttributes() {
        //given
        // Json Stub file is placed in src/test/resources/mappings

        //when
        RepositoryDetails repositoryDetails = controller.getRepositoryDetails(owner, repositoryName);

        //then
        assertThat(repositoryDetails.getFullName()).isEqualTo(fullName);
        assertThat(repositoryDetails.getDescription()).contains(description);
        assertThat(repositoryDetails.getCloneUrl()).isEqualTo(cloneUrl);
        assertThat(repositoryDetails.getCreatedAt()).isEqualTo(createdAt);
        assertThat(repositoryDetails.getStars()).isEqualTo(stars);
    }

    @Test
    public void verifyHeadersResponse() {
        //given
        // Json Stub file is placed in src/test/resources/mappings

        //when
        ResponseEntity<String> responseEntity = githubClient.getRepositoryDetails("John", "Repo");

        //then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("application/json", responseEntity.getHeaders().getFirst("Content-Type"));
        assertEquals("GitHub", responseEntity.getHeaders().getFirst("Server"));
    }
}
