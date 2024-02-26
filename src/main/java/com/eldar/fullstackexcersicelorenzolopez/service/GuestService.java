package com.eldar.fullstackexcersicelorenzolopez.service;

import com.eldar.fullstackexcersicelorenzolopez.domain.BirthdayGuest;
import com.eldar.fullstackexcersicelorenzolopez.enums.GuestStatusEnum;
import com.eldar.fullstackexcersicelorenzolopez.repository.GuestRepository;
import com.eldar.fullstackexcersicelorenzolopez.utils.ValidationUtils;
import com.eldar.fullstackexcersicelorenzolopez.vo.ConfirmedRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final MessageSource messageSource;

    public List<BirthdayGuest> findAll() {
        return guestRepository.findAll();
    }

    public BirthdayGuest findById(Long id) {
        Assert.notNull(id, this.getMessage("guest.id.null"));
        Optional<BirthdayGuest> guest = guestRepository.findById(id);
        if (guest.isPresent()) {
            return guest.get();
        } else {
            throw new IllegalArgumentException(this.getMessage("guest.id.invalid"));
        }
    }

    public void delete(Long id) {
        BirthdayGuest guest = this.findById(id);
        this.updatePositionsAtDeletion(guest);
        guestRepository.delete(guest);
    }

    public BirthdayGuest create(BirthdayGuest guest) {
        this.validateGuest(guest);
        guest.setStatus(GuestStatusEnum.POSSIBLE);
        return guestRepository.save(guest);
    }

    public BirthdayGuest update(BirthdayGuest guest) {
        this.validateGuest(guest);
        Assert.notNull(guest.getId(), this.getMessage("guest.id.null"));
        BirthdayGuest guestInDB = this.findById(guest.getId());
        this.updatePositions(guest, guestInDB);
        return guestRepository.save(guest);
    }

    public List<BirthdayGuest> updateMany(ConfirmedRequestVo confirmedRequestVo) {
        Assert.notNull(confirmedRequestVo.getConfirmedGuestIds(), this.getMessage("guest.null-list"));
        Assert.notEmpty(confirmedRequestVo.getConfirmedGuestIds(), this.getMessage("guest.empty-list"));
        List<BirthdayGuest> confirmedGuests = guestRepository.findAllById(confirmedRequestVo.getConfirmedGuestIds());
        Assert.isTrue(!confirmedGuests.isEmpty(), this.getMessage("guest.invalid-list"));
        List<BirthdayGuest> invitedGuests = confirmedGuests.stream().peek(guest -> {
            if (!guest.getStatus().equals(GuestStatusEnum.CONFIRMED)) {
                throw new IllegalArgumentException(this.getMessage("guest.not-confirmed"));
            }
            guest.setStatus(GuestStatusEnum.INVITED);
        }).toList();
        return guestRepository.saveAll(invitedGuests);
    }

    private void validateGuest(BirthdayGuest guest) {
        Assert.notNull(guest.getName(), this.getMessage("guest.name.null"));
        Assert.notNull(guest.getSurname(), this.getMessage("guest.surname.null"));
        Assert.notNull(guest.getPhoneNumber(), this.getMessage("guest.phone-number.null"));
        Assert.notNull(guest.getEmail(), this.getMessage("guest.email.null"));
        Assert.notNull(guest.getPosition(), this.getMessage("guest.position.null"));

        Assert.isTrue(!guest.getName().isBlank(), this.getMessage("guest.name.null"));
        Assert.isTrue(!guest.getSurname().isBlank(), this.getMessage("guest.surname.null"));
        Assert.isTrue(!guest.getPhoneNumber().isBlank(), this.getMessage("guest.phone-number.null"));
        Assert.isTrue(!guest.getEmail().isBlank(), this.getMessage("guest.email.null"));

        Assert.isTrue(ValidationUtils.stringIsOnlyCharacters(guest.getName()), this.getMessage("guest.name.invalid"));
        Assert.isTrue(ValidationUtils.stringIsOnlyCharacters(guest.getSurname()), this.getMessage("guest.surname.invalid"));
        Assert.isTrue(ValidationUtils.isValidEmail(guest.getEmail()), this.getMessage("guest.email.invalid"));
        Assert.isTrue(ValidationUtils.stringIsOnlyNumbers(guest.getPhoneNumber()), this.getMessage("guest.phone-number.invalid"));
        Assert.isTrue(guest.getPosition() > 0, this.getMessage("guest.position.invalid"));
    }
    
    private String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, null);
    }

    private void updatePositions(BirthdayGuest updatedGuest, BirthdayGuest oldGuest) {
        Integer newPosition = updatedGuest.getPosition();
        Integer oldPosition = oldGuest.getPosition();

        List<BirthdayGuest> guestsFromNewStatus = guestRepository.findAllByStatus(updatedGuest.getStatus());

        guestsFromNewStatus.forEach(guest -> {
            if (!guest.getId().equals(updatedGuest.getId()) && !newPosition.equals(oldPosition)) {
                if (newPosition > oldPosition) {
                    if (newPosition >= guest.getPosition() && oldPosition < guest.getPosition()) {
                        guest.setPosition(guest.getPosition() - 1);
                    }
                } else {
                    if (newPosition <= guest.getPosition() && oldPosition > guest.getPosition()) {
                        guest.setPosition(guest.getPosition() + 1);
                    }
                }
            }
        });
        guestRepository.saveAll(guestsFromNewStatus);

        if (!updatedGuest.getStatus().equals(oldGuest.getStatus())) {
            List<BirthdayGuest> guestsFromOldStatus = guestRepository.findAllByStatus(oldGuest.getStatus());
            guestsFromOldStatus.forEach(guest -> {
                if (newPosition <= guest.getPosition()) {
                    guest.setPosition(guest.getPosition() + 1);
                }
            });
            guestRepository.saveAll(guestsFromOldStatus);
        }
    }

    private void updatePositionsAtDeletion(BirthdayGuest deletedGuest) {
        List<BirthdayGuest> guests = guestRepository.findAllByStatus(deletedGuest.getStatus());
        guests.forEach(guest -> {
            if (!guest.getId().equals(deletedGuest.getId()) && deletedGuest.getPosition() >= guest.getPosition()) {
                guest.setPosition(guest.getPosition() - 1);
            }
        });
        guestRepository.saveAll(guests);
    }

}
