package com.github.kiskobotond.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String town;
    @Column(nullable = false)
    private Date lastSeen;

    public float distanceTo(Location location) {
        return 0;
    }
}
