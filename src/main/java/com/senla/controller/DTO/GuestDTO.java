package com.senla.controller.DTO;

import com.senla.model.Guest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String passport;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private Long roomId;
    @Getter
    @Setter
    private LocalDate checkInDate;
    @Getter
    @Setter
    private LocalDate checkOutDate;
    @Getter
    @Setter
    private List<MaintenanceInstanceDTO> orderedMaintenancesDtoList;

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
