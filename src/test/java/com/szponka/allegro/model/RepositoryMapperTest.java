package com.szponka.allegro.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:mock.properties")
public class RepositoryMapperTest {
    private final static Logger logger = LoggerFactory.getLogger(RepositoryMapperTest.class);

    @Autowired
    private RepositoryMapper repositoryMapper;
    @Value("${json_file_path}")
    private String jsonFilePath;
    @Value("${test_full_name}")
    private String fullName;

    @Test
    public void shouldCheckMappingJsonBodyToRepositoryDetails() {
        // given
        ResponseEntity<String> entity = responseEntityProvider();

        // when
        RepositoryDetails repositoryDetails = repositoryMapper.mapJsonBodyToRepositoryDetails(entity);

        // then
        assertEquals(fullName, repositoryDetails.getFullName());
    }

    @Test(expected = RuntimeException.class)
    public void shouldCheckMappingJsonBodyhEmptyBody() {
        // given
        ResponseEntity<String> entity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        // when
        repositoryMapper.mapJsonBodyToRepositoryDetails(entity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldCheckMappingJsonBodyToRepositoryWithWrongJsonBody() {
        // given
        ResponseEntity<String> entity = new ResponseEntity<>("Wrong Json Body", HttpStatus.OK);

        // when
        repositoryMapper.mapJsonBodyToRepositoryDetails(entity);
    }

    private ResponseEntity<String> responseEntityProvider() {
        String bodyString = "";
        try (FileInputStream input = new FileInputStream(jsonFilePath)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(
                    new InputStreamReader(input));
            bodyString = (jsonObject.toJSONString());

        } catch (FileNotFoundException e) {
            logger.error("JSON test file does not exist", e);
        } catch (IOException e) {
            logger.error("JSON test file exists, but there was IOException", e);
        } catch (ParseException e) {
            logger.error("JSON test file parse exception", e);
        }
        return new ResponseEntity<String>(bodyString, HttpStatus.OK);
    }
}
