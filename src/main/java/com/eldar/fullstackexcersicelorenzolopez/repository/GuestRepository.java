package com.eldar.fullstackexcersicelorenzolopez.repository;

import com.eldar.fullstackexcersicelorenzolopez.domain.BirthdayGuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<BirthdayGuest, Long> {
}
