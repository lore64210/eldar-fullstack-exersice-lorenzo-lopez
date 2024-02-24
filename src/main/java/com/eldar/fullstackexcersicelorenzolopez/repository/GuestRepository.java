package com.eldar.fullstackexerciselorenzolopez.repository;


import com.eldar.fullstackexerciselorenzolopez.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
