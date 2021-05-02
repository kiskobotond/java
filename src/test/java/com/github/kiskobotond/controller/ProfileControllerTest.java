package com.github.kiskobotond.controller;

import com.github.kiskobotond.model.Location;
import com.github.kiskobotond.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllProfiles() {
        //given

        //when
        String result = this.restTemplate.getForObject(String.format("http://localhost:%d/api/profiles", port), String.class);

        //then
        assertThat(result).isNullOrEmpty();
    }


    @Test
    void saveProfileSuccessfully() {
        //given
        Profile testProfile = Profile.builder()
                .emailAddress("a@b.com")
                .passwordHash("1234")
                .description("desc")
                .sex('M')
                .location(Location.builder()
                        .town("Budapest")
                        .lastSeen(new Date())
                        .build())
                .build();
        HttpEntity<Profile> request = new HttpEntity<>(testProfile);

        //when
        ResponseEntity<Profile> result = this.restTemplate.postForEntity(String.format("http://localhost:%d/api/profile", port), request, Profile.class);

        //then
        Profile body = result.getBody();
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(body).isNotNull(),
                () -> assertThat(body.getPasswordHash()).isEqualTo("1234"),
                () -> assertThat(body.getLocation().getTown()).isEqualTo("Budapest")
        );

        //cleanup
        deleteProfile(body.getEmailAddress());
    }

    @Test
    void saveProfileWithWrongLocation() {
        //given
        Profile testProfile = Profile.builder()
                .emailAddress("a@b.com")
                .passwordHash("1234")
                .location(Location.builder()
                        .lastSeen(new Date())
                        .build())
                .build();
        HttpEntity<Profile> request = new HttpEntity<>(testProfile);

        //when
        ResponseEntity<Profile> result = this.restTemplate.postForEntity(String.format("http://localhost:%d/api/profile", port), request, Profile.class);

        //then
        Profile body = result.getBody();
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR),
                () -> assertThat(body).isNull()
        );
    }

    @Test
    void saveProfileWithEmptyPassword() {
        //given
        Profile testProfile = Profile.builder()
                .emailAddress("a@b.com")
                .location(Location.builder()
                        .town("Budapest")
                        .lastSeen(new Date())
                        .build())
                .build();
        HttpEntity<Profile> request = new HttpEntity<>(testProfile);

        //when
        ResponseEntity<Profile> result = this.restTemplate.postForEntity(String.format("http://localhost:%d/api/profile", port), request, Profile.class);

        //then
        Profile body = result.getBody();
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR),
                () -> assertThat(body).isNull()
        );
    }

    private void deleteProfile(String id) {
        //given
        HttpEntity<String> request = new HttpEntity<>(null);

        //when
        ResponseEntity<String> result = this.restTemplate.exchange(String.format("http://localhost:%d/api/profile/%s", port, id), HttpMethod.DELETE, request, String.class);

        //then
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT),
                () -> assertThat(result.getBody()).isNull()
        );
    }
}