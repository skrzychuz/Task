package com.szponka.allegro.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GitHubClientTest {

    @Value("${repo_owner}")
    private String mockOwner;
    @Value("${repo_name}")
    private String mockName;
    @Value("${github_url}")
    private String gitHubUrl;

    private String fullUrl;

    @Mock
    private RestTemplate mockRestTemplate;

    @Spy
    @InjectMocks
    GitHubClient gitHubClient = new GitHubClient(mockRestTemplate);

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(gitHubClient, "github_url", gitHubUrl);
        fullUrl = gitHubUrl + mockOwner + "/" + mockName;
    }

    @Test
    public void shouldCheckResponseStatusCode200() {
        //given
        ResponseEntity<String> mockResponse = new ResponseEntity<>(HttpStatus.OK);
        when(mockRestTemplate.getForEntity(fullUrl, String.class)).thenReturn(mockResponse);

        //when
        ResponseEntity<String> response = gitHubClient.getRepositoryDetails(mockOwner, mockName);

        //then
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldCheckResponseStatusCode500() {
        //given
        ResponseEntity<String> mockResponse = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(mockRestTemplate.getForEntity(fullUrl, String.class)).thenReturn(mockResponse);

        //when
        ResponseEntity<String> response = gitHubClient.getRepositoryDetails(mockOwner, mockName);

        //then
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    public void shouldCheckResponseStatusCode502() {
        //given
        ResponseEntity<String> mockResponse = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        when(mockRestTemplate.getForEntity(fullUrl, String.class)).thenReturn(mockResponse);

        //when
        ResponseEntity<String> response = gitHubClient.getRepositoryDetails(mockOwner, mockName);

        //then
        assertEquals(502, response.getStatusCodeValue());
    }

    @Test
    public void shouldCheckResponseStatusCode404() {
        //given
        ResponseEntity<String> mockResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(mockRestTemplate.getForEntity(fullUrl, String.class)).thenReturn(mockResponse);

        //when
        ResponseEntity<String> response = gitHubClient.getRepositoryDetails(mockOwner, mockName);

        //then
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldCheckResponseStatusCode400() {
        //given
        ResponseEntity<String> mockResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(mockRestTemplate.getForEntity(fullUrl, String.class)).thenReturn(mockResponse);

        //when
        ResponseEntity<String> response = gitHubClient.getRepositoryDetails(mockOwner, mockName);

        //then
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldCheckHttpServerErrorExceptionHandling() {
        //given
        when(mockRestTemplate.getForEntity(fullUrl, String.class)).thenThrow(new HttpServerErrorException(HttpStatus.BAD_GATEWAY));

        //when
        gitHubClient.getRepositoryDetails(mockOwner, mockName);
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldCheckResponseHttpClientErrorExceptionHandling() {
        //given
        when(mockRestTemplate.getForEntity(fullUrl, String.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        //when
        gitHubClient.getRepositoryDetails(mockOwner, mockName);
    }

    @Test(expected = RestClientException.class)
    public void shouldCheckResponseRestClientExceptionHandling() {
        //given
        when(mockRestTemplate.getForEntity(fullUrl, String.class)).thenThrow(RestClientException.class);

        //when
        gitHubClient.getRepositoryDetails(mockOwner, mockName);
    }
}