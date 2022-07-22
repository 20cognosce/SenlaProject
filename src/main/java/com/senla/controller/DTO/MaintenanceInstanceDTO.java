package com.senla.controller.DTO;

import com.senla.model.Maintenance;
import com.senla.model.MaintenanceCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceInstanceDTO {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private MaintenanceCategory category;
    @Getter
    @Setter
    private LocalDateTime orderTime;
    @Getter
    @Setter
    private String guestName;
    @Getter
    @Setter
    private Long guestId;

    public MaintenanceInstanceDTO(Maintenance maintenance) {
        this.id = maintenance.getId();
        this.name = maintenance.getName();
        this.category = maintenance.getCategory();
        this.price = maintenance.getPrice();
        this.orderTime = maintenance.getOrderTime();
        this.guestName = maintenance.getGuest().getName();
        this.guestId = maintenance.getGuest().getId();
    }
}
