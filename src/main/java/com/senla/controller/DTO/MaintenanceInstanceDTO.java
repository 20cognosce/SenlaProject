package com.senla.controller.DTO;

import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.model.MaintenanceCategory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MaintenanceInstanceDTO {

    private final Long originalMaintenanceId;
    private final String name;
    private final int price;
    private final MaintenanceCategory category;
    private final List<LocalDateTime> orderTimes;
    private final String guestName;
    private final Long guestId;

    public MaintenanceInstanceDTO(Maintenance maintenance, Guest guest, List<LocalDateTime> orderTimes) {
        this.originalMaintenanceId = maintenance.getId();
        this.name = maintenance.getName();
        this.category = maintenance.getCategory();
        this.price = maintenance.getPrice();
        this.orderTimes = orderTimes;
        this.guestName = guest.getName();
        this.guestId = guest.getId();
    }
}
