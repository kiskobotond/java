package com.github.kiskobotond.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
    private String passwordHash;
    private String description;
    private char sex;

    @OneToOne
    private Location location;

//    @OneToMany
//    private List<FriendRequest> friendRequests;
}
