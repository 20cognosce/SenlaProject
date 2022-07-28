package com.senla.controller.DTO;

import com.senla.model.Guest;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
public class GuestDTO {

    private final Long id;
    private final String name;
    private final String passport;
    private final int price;
    private final Long roomId;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final List<MaintenanceInstanceDTO> orderedMaintenancesDtoList;

    public GuestDTO(Guest guest, List<MaintenanceInstanceDTO> maintenanceInstanceDtoList) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.passport = guest.getPassport();
        this.price = guest.getPrice();
        if (Objects.isNull(guest.getRoom())) {
            this.roomId = 0L;
        } else {
            this.roomId = guest.getRoom().getId();
        }

        this.checkInDate = guest.getCheckInDate();
        this.checkOutDate = guest.getCheckOutDate();
        this.orderedMaintenancesDtoList = maintenanceInstanceDtoList;
    }
}
