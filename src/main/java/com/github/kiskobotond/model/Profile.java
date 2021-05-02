package com.github.kiskobotond.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Profile {

    @Id
    private String emailAddress;
    @Column(nullable = false)
    private String passwordHash;
    private String description;
    private char sex;

    @OneToOne(cascade = {CascadeType.ALL})
    private Location location;

    @OneToMany(mappedBy = "receiver", cascade = {CascadeType.ALL})
    private List<FriendRequest> receivedFriendRequests;

    @OneToMany(mappedBy = "sender", cascade = {CascadeType.ALL})
    private List<FriendRequest> sentFriendRequests;
}
