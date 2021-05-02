package com.github.kiskobotond.controller;

import com.github.kiskobotond.model.FriendRequest;
import com.github.kiskobotond.model.Location;
import com.github.kiskobotond.model.Profile;
import com.github.kiskobotond.model.Status;
import org.assertj.core.util.Lists;
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
class FriendRequestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void saveFriendRequestSuccessfully() {
        //given
        Profile profile = createProfile();
        FriendRequest testFriendRequest = FriendRequest.builder()
                .receiver(profile)
                .sender(profile)
                .status(Status.PENDING)
                .build();

        HttpEntity<FriendRequest> request = new HttpEntity<>(testFriendRequest);

        //when
        ResponseEntity<FriendRequest> result = this.restTemplate.postForEntity(String.format("http://localhost:%d/api/friend/request", port), request, FriendRequest.class);

        //then
        FriendRequest body = result.getBody();
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(body).isNotNull(),
                () -> assertThat(body.getStatus()).isEqualTo(Status.PENDING),
                () -> assertThat(body.getSender().getLocation().getTown()).isEqualTo("Budapest")
        );

        //cleanup
        deleteProfile(profile.getEmailAddress());
    }

    private Profile createProfile() {
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
        Profile resultProfile = result.getBody();
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(resultProfile).isNotNull(),
                () -> assertThat(resultProfile.getPasswordHash()).isEqualTo("1234"),
                () -> assertThat(resultProfile.getLocation().getTown()).isEqualTo("Budapest")
        );
        return resultProfile;
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