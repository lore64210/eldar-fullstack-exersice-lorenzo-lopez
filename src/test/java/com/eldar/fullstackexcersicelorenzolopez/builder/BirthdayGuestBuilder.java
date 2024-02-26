package com.eldar.fullstackexcersicelorenzolopez.builder;

import com.eldar.fullstackexcersicelorenzolopez.domain.BirthdayGuest;
import com.eldar.fullstackexcersicelorenzolopez.enums.GuestStatusEnum;
import com.eldar.fullstackexcersicelorenzolopez.utils.RandomUtils;
import jakarta.persistence.EntityManager;

public class BirthdayGuestBuilder {
    private BirthdayGuest guest;

    private BirthdayGuestBuilder() {
        this.guest = new BirthdayGuest();
    }

    public static BirthdayGuestBuilder getInstance() {
        return new BirthdayGuestBuilder();
    }

    public BirthdayGuest build() {
        return this.guest;
    }

    public BirthdayGuest build(EntityManager entityManager) {
        entityManager.persist(this.guest);
        return this.guest;
    }

    public BirthdayGuestBuilder createGeneric() {
        setGenericValues();
        return this;
    }

    public BirthdayGuestBuilder withId(Long value) {
        this.guest.setId(value);
        return this;
    }

    public BirthdayGuestBuilder withName(String value) {
        this.guest.setName(value);
        return this;
    }

    public BirthdayGuestBuilder withSurname(String value) {
        this.guest.setSurname(value);
        return this;
    }

    public BirthdayGuestBuilder withPhoneNumber(String value) {
        this.guest.setPhoneNumber(value);
        return this;
    }

    public BirthdayGuestBuilder withEmail(String value) {
        this.guest.setEmail(value);
        return this;
    }

    public BirthdayGuestBuilder withStatus(GuestStatusEnum value) {
        this.guest.setStatus(value);
        return this;
    }

    public BirthdayGuestBuilder withPosition(Integer value) {
        this.guest.setPosition(value);
        return this;
    }

    private void setGenericValues() {
        this.guest.setName(RandomUtils.generateRandomString(10));
        this.guest.setSurname(RandomUtils.generateRandomString(10));
        this.guest.setEmail(RandomUtils.generateRandomString(5) + "@" + RandomUtils.generateRandomString(5) + ".com");
        this.guest.setStatus(GuestStatusEnum.POSSIBLE);
        this.guest.setPhoneNumber(Integer.toString(RandomUtils.generateRandomInteger(10000000, 99999999)));
        this.guest.setPosition(1);
    }

}
