package com.github.kiskobotond.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne
    @JoinColumn(nullable=false)
    private Profile sender;

    @ManyToOne
    @JoinColumn(nullable=false)
    private Profile receiver;
    @Enumerated(EnumType.STRING)
    private Status status;
}
