package com.github.kiskobotond.controller;

import com.github.kiskobotond.model.FriendRequest;
import com.github.kiskobotond.repository.FriendRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class FriendRequestController {
    @Autowired
    FriendRequestRepository friendRequestRepository;

    @PostMapping("/friend/request")
    public ResponseEntity<FriendRequest> createFriendRequest(@RequestBody FriendRequest friendRequest) {
        try {
            FriendRequest _friendRequest = friendRequestRepository.save(friendRequest);
            return new ResponseEntity<>(_friendRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during save friend request", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
