package com.senla.controller.DTO;

import com.senla.model.Maintenance;
import com.senla.model.MaintenanceCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MaintenanceTemplateDTO {

    private Long id;
    private String name;
    private Integer price;
    private MaintenanceCategory category;

    public MaintenanceTemplateDTO(Maintenance maintenance) {
        this.id = maintenance.getId();
        this.name = maintenance.getName();
        this.category = maintenance.getCategory();
        this.price = maintenance.getPrice();
    }
}
