package com.eldar.fullstackexcersicelorenzolopez.domain;

import com.eldar.fullstackexcersicelorenzolopez.enums.GuestStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "guest")
public class BirthdayGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber; // Uso un string por si el telefono empieza con ceros
    private String email;
    @Enumerated(EnumType.STRING)
    private GuestStatusEnum status;


}
