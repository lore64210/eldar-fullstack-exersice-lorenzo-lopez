package com.eldar.fullstackexcersicelorenzolopez.vo;

import com.eldar.fullstackexcersicelorenzolopez.domain.BirthdayGuest;
import lombok.Data;

import java.util.List;

@Data
public class ConfirmedRequestVo {
    private List<Long> confirmedGuestIds;
}
