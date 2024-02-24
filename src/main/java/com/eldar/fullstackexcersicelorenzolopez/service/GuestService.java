package com.eldar.fullstackexerciselorenzolopez.service;

import com.eldar.fullstackexerciselorenzolopez.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
}
