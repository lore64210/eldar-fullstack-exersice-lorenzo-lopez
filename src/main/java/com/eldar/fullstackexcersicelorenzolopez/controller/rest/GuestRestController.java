package com.eldar.fullstackexcersicelorenzolopez.controller.rest;

import com.eldar.fullstackexcersicelorenzolopez.domain.BirthdayGuest;
import com.eldar.fullstackexcersicelorenzolopez.service.GuestService;
import com.eldar.fullstackexcersicelorenzolopez.vo.ConfirmedRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestRestController {

    private final GuestService guestService;

    @GetMapping
    public ResponseEntity<List<BirthdayGuest>> findAll() {
        return ResponseEntity.ok(guestService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BirthdayGuest> findById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BirthdayGuest> save(@RequestBody BirthdayGuest birthdayGuest) {
        return ResponseEntity.ok(guestService.create(birthdayGuest));
    }

    /*
    * Considero que si bien en las api rest
    * se especifica el id del objeto a modificar en la url
    * estaria exponiendo informacion innecesaria
    * por lo tanto uso el id que llega en el cuerpo de la peticion
    * */
    @PutMapping
    public ResponseEntity<BirthdayGuest> edit(@RequestBody BirthdayGuest guest) {
        return ResponseEntity.ok(guestService.update(guest));
    }

    @PutMapping("/invite-confirmed")
    public ResponseEntity<List<BirthdayGuest>> updateMany(@RequestBody ConfirmedRequestVo confirmedGuestsRequestVo) {
        return ResponseEntity.ok(guestService.updateMany(confirmedGuestsRequestVo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
