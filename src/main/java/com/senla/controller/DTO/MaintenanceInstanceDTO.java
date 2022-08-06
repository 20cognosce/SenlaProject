package com.senla.controller.DTO;

import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.model.MaintenanceCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MaintenanceInstanceDTO {

    private Long originalMaintenanceId;
    private String name;
    private Integer price;
    private MaintenanceCategory category;
    private List<LocalDateTime> orderTimes;
    private String guestName;
    private Long guestId;

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
