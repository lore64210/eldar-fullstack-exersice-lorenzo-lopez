package com.eldar.fullstackexerciselorenzolopez.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "guest")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}
