package com.github.kiskobotond.repository;

import com.github.kiskobotond.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {
}
