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
        Assert.notNull(id, messageSource.getMessage("guest.id.null", null, null));
        Optional<BirthdayGuest> guest = guestRepository.findById(id);
        if (guest.isPresent()) {
            return guest.get();
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("guest.id.invalid", null, null));
        }
    }

    public void delete(Long id) {
        BirthdayGuest guest = this.findById(id);
        guestRepository.delete(guest);
    }

    public BirthdayGuest create(BirthdayGuest guest) {
        this.validateGuest(guest);
        return guestRepository.save(guest);
    }

    public BirthdayGuest update(BirthdayGuest guest) {
        this.validateGuest(guest);
        Assert.notNull(guest.getId(), messageSource.getMessage("guest.id.null", null, null));
        if (guestRepository.existsById(guest.getId())) {
            return guestRepository.save(guest);
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("guest.id.invalid", null, null));
        }
    }

    public List<BirthdayGuest> updateMany(ConfirmedRequestVo confirmedRequestVo) {
        Assert.notNull(confirmedRequestVo.getConfirmedGuestIds(), messageSource.getMessage("guest.null-list", null, null));
        Assert.notEmpty(confirmedRequestVo.getConfirmedGuestIds(), messageSource.getMessage("guest.empty-list", null, null));
        List<BirthdayGuest> confirmedGuests = guestRepository.findAllById(confirmedRequestVo.getConfirmedGuestIds());
        Assert.isTrue(!confirmedGuests.isEmpty(), messageSource.getMessage("guest.invalid-list", null, null));
        List<BirthdayGuest> invitedGuests = confirmedGuests.stream().peek(guest -> {
            if (!guest.getStatus().equals(GuestStatusEnum.CONFIRMED)) {
                throw new IllegalArgumentException(messageSource.getMessage("guest.not-confirmed", null, null));
            }
            guest.setStatus(GuestStatusEnum.INVITED);
        }).toList();
        return guestRepository.saveAll(invitedGuests);
    }

    private void validateGuest(BirthdayGuest guest) {
        Assert.notNull(guest.getName(), messageSource.getMessage("guest.name.null", null, null));
        Assert.notNull(guest.getSurname(), messageSource.getMessage("guest.surname.null", null, null));
        Assert.notNull(guest.getPhoneNumber(), messageSource.getMessage("guest.phone-number.null", null, null));
        Assert.notNull(guest.getEmail(), messageSource.getMessage("guest.email.null", null, null));

        Assert.isTrue(!guest.getName().isBlank(), messageSource.getMessage("guest.name.null", null, null));
        Assert.isTrue(!guest.getSurname().isBlank(), messageSource.getMessage("guest.surname.null", null, null));
        Assert.isTrue(!guest.getPhoneNumber().isBlank(), messageSource.getMessage("guest.phone-number.null", null, null));
        Assert.isTrue(!guest.getEmail().isBlank(), messageSource.getMessage("guest.email.null", null, null));

        Assert.isTrue(ValidationUtils.stringContainsNumbers(guest.getName()), messageSource.getMessage("guest.name.invalid", null, null));
        Assert.isTrue(ValidationUtils.stringContainsNumbers(guest.getSurname()), messageSource.getMessage("guest.surname.invalid", null, null));
        Assert.isTrue(ValidationUtils.isValidEmail(guest.getEmail()), messageSource.getMessage("guest.email.invalid", null, null));
        Assert.isTrue(ValidationUtils.stringIsOnlyNumbers(guest.getPhoneNumber()), messageSource.getMessage("guest.phone-number.invalid", null, null));
    }

}
