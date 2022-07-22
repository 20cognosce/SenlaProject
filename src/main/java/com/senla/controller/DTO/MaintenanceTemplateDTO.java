package com.senla.controller.DTO;

import com.senla.model.Maintenance;
import com.senla.model.MaintenanceCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTemplateDTO {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer price;
    @Getter
    @Setter
    private MaintenanceCategory category;

    public MaintenanceTemplateDTO(Maintenance maintenance) {
        this.id = maintenance.getId();
        this.name = maintenance.getName();
        this.category = maintenance.getCategory();
        this.price = maintenance.getPrice();
    }
}
