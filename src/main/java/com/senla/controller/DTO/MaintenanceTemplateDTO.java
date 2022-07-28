package com.senla.controller.DTO;

import com.senla.model.Maintenance;
import com.senla.model.MaintenanceCategory;
import lombok.Data;

@Data
public class MaintenanceTemplateDTO {

    private final Long id;
    private final String name;
    private final Integer price;
    private final MaintenanceCategory category;

    public MaintenanceTemplateDTO(Maintenance maintenance) {
        this.id = maintenance.getId();
        this.name = maintenance.getName();
        this.category = maintenance.getCategory();
        this.price = maintenance.getPrice();
    }
}
