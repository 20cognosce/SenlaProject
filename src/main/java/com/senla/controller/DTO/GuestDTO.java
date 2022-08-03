package com.senla.controller.DTO;

import com.senla.model.Guest;
import com.senla.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class GuestDTO {

    private Long id;
    private String name;
    private String passport;
    private Role role;
    private Integer price;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private List<MaintenanceInstanceDTO> orderedMaintenancesDtoList;

    public GuestDTO(Guest guest, List<MaintenanceInstanceDTO> maintenanceInstanceDtoList) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.passport = guest.getPassport();
        this.role = guest.getRole();
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
