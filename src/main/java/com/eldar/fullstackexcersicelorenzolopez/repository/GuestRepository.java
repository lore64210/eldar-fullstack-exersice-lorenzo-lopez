package com.eldar.fullstackexcersicelorenzolopez.repository;

import com.eldar.fullstackexcersicelorenzolopez.domain.BirthdayGuest;
import com.eldar.fullstackexcersicelorenzolopez.enums.GuestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<BirthdayGuest, Long> {
    List<BirthdayGuest> findAllByStatus(GuestStatusEnum status);
}
