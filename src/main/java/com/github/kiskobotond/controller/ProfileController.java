package com.github.kiskobotond.controller;

import com.github.kiskobotond.model.Profile;
import com.github.kiskobotond.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
            log.error("Error during requesting profiles", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        try {
            Profile _profile = profileRepository.save(profile);
            return new ResponseEntity<>(_profile, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during save profile", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable String id) {
        try {
            profileRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error during delete profile", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
