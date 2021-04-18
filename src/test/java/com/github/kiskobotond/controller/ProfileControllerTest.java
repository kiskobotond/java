package com.github.kiskobotond.controller;

import com.github.kiskobotond.model.Location;
import com.github.kiskobotond.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

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
    void saveProfile() {
        //given
        Profile testProfile= Profile.builder()
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
        Profile result = this.restTemplate.postForObject(String.format("http://localhost:%d/api/profiles", port), request, Profile.class);

        //then
        assertThat(result).isNotNull();
    }
}