package com.github.kiskobotond.repository;

import com.github.kiskobotond.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
}
