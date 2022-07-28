package com.senla.controller.converters;

import com.senla.controller.DTO.MaintenanceInstanceDTO;
import com.senla.controller.DTO.MaintenanceTemplateDTO;
import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MaintenanceConverter implements Converter<Maintenance, MaintenanceTemplateDTO> {

    private final MaintenanceService maintenanceService;
    private final GuestService guestService;

    @Override
    public MaintenanceTemplateDTO convert(@NonNull Maintenance maintenance) {
        return new MaintenanceTemplateDTO(maintenance);
    }

    public MaintenanceInstanceDTO toMaintenanceInstanceDTO(Long maintenanceId, Long guestId) {
        Maintenance maintenance = maintenanceService.getById(maintenanceId);
        Guest guest = guestService.getById(guestId);
        List<LocalDateTime> orderTimes = maintenanceService.getGuest2MaintenanceOrderTime(guestId, maintenanceId);
        return new MaintenanceInstanceDTO(maintenance, guest, orderTimes);
    }

    public Maintenance toMaintenance(MaintenanceTemplateDTO maintenanceTemplateDTO) {
        return new Maintenance(maintenanceTemplateDTO.getName(),
                maintenanceTemplateDTO.getPrice(),
                maintenanceTemplateDTO.getCategory(),
                null);
    }
}
