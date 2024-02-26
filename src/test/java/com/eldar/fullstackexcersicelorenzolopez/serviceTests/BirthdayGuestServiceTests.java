package com.eldar.fullstackexcersicelorenzolopez.serviceTests;

import com.eldar.fullstackexcersicelorenzolopez.FullstackExerciseLorenzoLopezApplicationTests;
import com.eldar.fullstackexcersicelorenzolopez.builder.BirthdayGuestBuilder;
import com.eldar.fullstackexcersicelorenzolopez.domain.BirthdayGuest;
import com.eldar.fullstackexcersicelorenzolopez.enums.GuestStatusEnum;
import com.eldar.fullstackexcersicelorenzolopez.service.GuestService;
import com.eldar.fullstackexcersicelorenzolopez.utils.RandomUtils;
import com.eldar.fullstackexcersicelorenzolopez.vo.ConfirmedRequestVo;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Collections;
import java.util.List;

@Transactional
public class BirthdayGuestServiceTests extends FullstackExerciseLorenzoLopezApplicationTests {

    @Autowired
    private GuestService guestService;

    @Test
    public void findAll_withExistentGuestsInDB_returnsGuestsInDB() {
        int amount = 4;
        for (int i = 0; i < amount; i++) {
            BirthdayGuestBuilder.getInstance().createGeneric().build(entityManager);
        }

        List<BirthdayGuest> guestList = guestService.findAll();

        Assertions.assertThat(guestList.size()).isEqualTo(amount);
    }

    @Test
    public void findAll_withNoGuestsInDB_returnsEmptyList() {
        List<BirthdayGuest> guestList = guestService.findAll();

        Assertions.assertThat(guestList.size()).isEqualTo(0);
    }

    @Test
    public void findById_withExistentGuestInDB_returnsGuestInDB() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);

        BirthdayGuest guestInDB = guestService.findById(guest.getId());

        Assertions.assertThat(guestInDB.getId()).isNotNull();
        Assertions.assertThat(guestInDB.getId()).isEqualTo(guestInDB.getId());
    }

    @Test
    public void findById_withNullId_throwsException() {
        Assertions.assertThatThrownBy(() -> guestService.findById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.id.null"));
    }

    @Test
    public void findById_withNonExistentId_throwsException() {
        Assertions.assertThatThrownBy(() -> guestService.findById(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.id.invalid"));
    }

    @Test
    public void create_withValidGuestData_createsGuestAndReturnsIt() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build();

        BirthdayGuest guestInDB = guestService.create(guest);

        Assertions.assertThat(guestInDB.getId()).isNotNull();
        Assertions.assertThat(guestInDB.getName()).isEqualTo(guest.getName());
        Assertions.assertThat(guestInDB.getSurname()).isEqualTo(guest.getSurname());
        Assertions.assertThat(guestInDB.getEmail()).isEqualTo(guest.getEmail());
        Assertions.assertThat(guestInDB.getPhoneNumber()).isEqualTo(guest.getPhoneNumber());
        Assertions.assertThat(guestInDB.getStatus()).isEqualTo(GuestStatusEnum.POSSIBLE);
    }

    @Test
    public void create_withValidGuestDataAndSomeStatus_createsGuestAndReturnsItWithStatusPossible() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withStatus(GuestStatusEnum.CONFIRMED)
                .build();

        BirthdayGuest guestInDB = guestService.create(guest);

        Assertions.assertThat(guestInDB.getId()).isNotNull();
        Assertions.assertThat(guestInDB.getName()).isEqualTo(guest.getName());
        Assertions.assertThat(guestInDB.getSurname()).isEqualTo(guest.getSurname());
        Assertions.assertThat(guestInDB.getEmail()).isEqualTo(guest.getEmail());
        Assertions.assertThat(guestInDB.getPhoneNumber()).isEqualTo(guest.getPhoneNumber());
        Assertions.assertThat(guestInDB.getStatus()).isEqualTo(GuestStatusEnum.POSSIBLE);
    }

    @Test
    public void create_withNullName_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withName(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.name.null"));
    }

    @Test
    public void create_withInvalidName_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withName(Integer.toString(RandomUtils.generateRandomInteger(0, 99)))
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.name.invalid"));
    }

    @Test
    public void create_withNullSurname_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withSurname(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.surname.null"));
    }

    @Test
    public void create_withInvalidSurname_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withSurname(Integer.toString(RandomUtils.generateRandomInteger(0, 99)))
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.surname.invalid"));
    }

    @Test
    public void create_withNullPhoneNumber_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withPhoneNumber(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.phone-number.null"));
    }

    @Test
    public void create_withInvalidPhoneNumber_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withPhoneNumber(RandomUtils.generateRandomString(10))
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.phone-number.invalid"));
    }

    @Test
    public void create_withNullEmail_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withEmail(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.email.null"));
    }

    @Test
    public void create_withInvalidEmail_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withEmail(RandomUtils.generateRandomString(10))
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.email.invalid"));
    }

    @Test
    public void create_withNullPosition_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withPosition(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.position.null"));
    }

    @Test
    public void create_withInvalidPosition_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withPosition(-1)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.create(guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.position.invalid"));
    }

    @Test
    public void update_withValidGuestData_createsGuestAndReturnsIt() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .build();

        BirthdayGuest guestInDB = guestService.update(guestUpdated);

        Assertions.assertThat(guestInDB.getId()).isNotNull();
        Assertions.assertThat(guestInDB.getId()).isEqualTo(guestUpdated.getId());
        Assertions.assertThat(guestInDB.getName()).isEqualTo(guestUpdated.getName());
        Assertions.assertThat(guestInDB.getSurname()).isEqualTo(guestUpdated.getSurname());
        Assertions.assertThat(guestInDB.getEmail()).isEqualTo(guestUpdated.getEmail());
        Assertions.assertThat(guestInDB.getPhoneNumber()).isEqualTo(guestUpdated.getPhoneNumber());
        Assertions.assertThat(guestInDB.getStatus()).isEqualTo(guestUpdated.getStatus());
    }

    @Test
    public void update_withNullName_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .withName(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.update(guestUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.name.null"));
    }

    @Test
    public void update_withInvalidName_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .withName(Integer.toString(RandomUtils.generateRandomInteger(1, 100)))
                .build();

        Assertions.assertThatThrownBy(() -> guestService.update(guestUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.name.invalid"));
    }

    @Test
    public void update_withNullSurname_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .withSurname(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.update(guestUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.surname.null"));
    }

    @Test
    public void update_withInvalidSurname_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .withSurname(Integer.toString(RandomUtils.generateRandomInteger(1, 100)))
                .build();

        Assertions.assertThatThrownBy(() -> guestService.update(guestUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.surname.invalid"));
    }

    @Test
    public void update_withNullPhoneNumber_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .withPhoneNumber(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.update(guestUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.phone-number.null"));
    }

    @Test
    public void update_withInvalidPhoneNumber_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .withPhoneNumber(RandomUtils.generateRandomString(10))
                .build();

        Assertions.assertThatThrownBy(() -> guestService.update(guestUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.phone-number.invalid"));
    }

    @Test
    public void update_withNullEmail_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .withEmail(null)
                .build();

        Assertions.assertThatThrownBy(() -> guestService.update(guestUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.email.null"));
    }

    @Test
    public void update_withInvalidEmail_throwsException() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric().build(entityManager);
        BirthdayGuest guestUpdated = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withId(guest.getId())
                .withEmail(RandomUtils.generateRandomString(10))
                .build();

        Assertions.assertThatThrownBy(() -> guestService.update(guestUpdated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.email.invalid"));
    }

    @Test
    public void delete_withValidId_deletesGuestFromDB() {
        BirthdayGuest guest = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .build(entityManager);
        entityManager.flush();
        int rowsBeforeDelete = JdbcTestUtils.countRowsInTable(jdbcTemplate, "guest");

        guestService.delete(guest.getId());

        entityManager.flush();
        int rowsAfterDelete = JdbcTestUtils.countRowsInTable(jdbcTemplate, "guest");

        Assertions.assertThat(rowsAfterDelete).isEqualTo(rowsBeforeDelete - 1);
    }

    @Test
    public void delete_withValidIdAndOtherGuestsWithSameStatus_deletesGuestFromDBAndUpdatesPositions() {
        BirthdayGuest guest1 = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withStatus(GuestStatusEnum.CONFIRMED)
                .withPosition(1)
                .build(entityManager);
        BirthdayGuest guest2 = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withStatus(GuestStatusEnum.CONFIRMED)
                .withPosition(2)
                .build(entityManager);
        BirthdayGuest guest3 = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withStatus(GuestStatusEnum.CONFIRMED)
                .withPosition(3)
                .build(entityManager);
        BirthdayGuest guest4 = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withStatus(GuestStatusEnum.CONFIRMED)
                .withPosition(4)
                .build(entityManager);

        entityManager.flush();
        int rowsBeforeDelete = JdbcTestUtils.countRowsInTable(jdbcTemplate, "guest");

        guestService.delete(guest1.getId());

        entityManager.flush();
        int rowsAfterDelete = JdbcTestUtils.countRowsInTable(jdbcTemplate, "guest");


        Assertions.assertThat(rowsAfterDelete).isEqualTo(rowsBeforeDelete - 1);
        Assertions.assertThat(guest2.getPosition()).isEqualTo(1);
        Assertions.assertThat(guest3.getPosition()).isEqualTo(2);
        Assertions.assertThat(guest4.getPosition()).isEqualTo(3);
    }

    @Test
    public void delete_withNullId_throwsException() {
        Assertions.assertThatThrownBy(() -> guestService.delete(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.id.null"));
    }

    @Test
    public void delete_withInvalidId_throwsException() {
        Assertions.assertThatThrownBy(() -> guestService.delete(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.id.invalid"));
    }

    @Test
    public void updateMany_withValidGuestIds_updatesEveryGuestWithStatusInvited() {
        BirthdayGuest guest1 = BirthdayGuestBuilder
            .getInstance()
            .createGeneric()
            .withStatus(GuestStatusEnum.CONFIRMED)
            .build(entityManager);
        BirthdayGuest guest2 = BirthdayGuestBuilder
            .getInstance()
            .createGeneric()
            .withStatus(GuestStatusEnum.CONFIRMED)
            .build(entityManager);
        BirthdayGuest guest3 = BirthdayGuestBuilder
            .getInstance()
            .createGeneric()
            .withStatus(GuestStatusEnum.CONFIRMED)
            .build(entityManager);

        ConfirmedRequestVo confirmedRequestVo = new ConfirmedRequestVo();
        confirmedRequestVo.setConfirmedGuestIds(List.of(guest1.getId(), guest2.getId(), guest3.getId()));

        List<BirthdayGuest> guests = guestService.updateMany(confirmedRequestVo);

        Assertions.assertThat(guests.size()).isEqualTo(3);
        Assertions.assertThat(guest1.getStatus()).isEqualTo(GuestStatusEnum.INVITED);
        Assertions.assertThat(guest2.getStatus()).isEqualTo(GuestStatusEnum.INVITED);
        Assertions.assertThat(guest3.getStatus()).isEqualTo(GuestStatusEnum.INVITED);
    }

    @Test
    public void updateMany_withNonExistentIdInList_throwsException() {
        ConfirmedRequestVo confirmedRequestVo = new ConfirmedRequestVo();
        confirmedRequestVo.setConfirmedGuestIds(List.of(-1L));
        Assertions.assertThatThrownBy(() -> guestService.updateMany(confirmedRequestVo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.invalid-list"));
    }

    @Test
    public void updateMany_withGuestNotConfirmed_throwsException() {
        BirthdayGuest guest1 = BirthdayGuestBuilder
                .getInstance()
                .createGeneric()
                .withStatus(GuestStatusEnum.POSSIBLE)
                .build(entityManager);

        ConfirmedRequestVo confirmedRequestVo = new ConfirmedRequestVo();
        confirmedRequestVo.setConfirmedGuestIds(List.of(guest1.getId()));
        Assertions.assertThatThrownBy(() -> guestService.updateMany(confirmedRequestVo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.not-confirmed"));
    }

    @Test
    public void updateMany_withNullList_throwsException() {
        ConfirmedRequestVo confirmedRequestVo = new ConfirmedRequestVo();
        confirmedRequestVo.setConfirmedGuestIds(null);
        Assertions.assertThatThrownBy(() -> guestService.updateMany(confirmedRequestVo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.null-list"));
    }

    @Test
    public void updateMany_withEmptyList_throwsException() {
        ConfirmedRequestVo confirmedRequestVo = new ConfirmedRequestVo();
        confirmedRequestVo.setConfirmedGuestIds(Collections.emptyList());
        Assertions.assertThatThrownBy(() -> guestService.updateMany(confirmedRequestVo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(this.getMessage("guest.empty-list"));
    }

}
