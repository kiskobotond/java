package com.github.kiskobotond.controller;

import com.github.kiskobotond.model.Profile;
import com.github.kiskobotond.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProfileController {
    @Autowired
    ProfileRepository profileRepository;

    @GetMapping("/profiles")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        try {
            List<Profile> profiles = new ArrayList<>();

            profileRepository.findAll().forEach(profiles::add);

            if (profiles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(profiles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profiles")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        try {
            Profile _profile = profileRepository.save(new Profile(
                    profile.getEmailAddress(),
                    profile.getPasswordHash(),
                    profile.getDescription(),
                    profile.getSex(),
                    profile.getLocation()
//                    profile.getFriendRequests()
            ));
            return new ResponseEntity<>(_profile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
