package com.eldar.fullstackexerciselorenzolopez.controller.rest;

import com.eldar.fullstackexerciselorenzolopez.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestRestController {

    private final GuestService guestService;


}
