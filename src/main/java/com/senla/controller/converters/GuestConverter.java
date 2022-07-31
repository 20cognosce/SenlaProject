package com.senla.controller.converters;

import com.senla.controller.DTO.GuestCreationDTO;
import com.senla.controller.DTO.GuestDTO;
import com.senla.model.Guest;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class GuestConverter implements Converter <Guest, GuestDTO> {

    private final GuestService guestService;
    private final MaintenanceService maintenanceService;
    private final MaintenanceConverter maintenanceConverter;

    @Override
    public GuestDTO convert(@NonNull Guest guest) {
        return new GuestDTO(guest, maintenanceService.sortMaintenancesOfGuestByAddition(guest.getId(), "asc")
                .stream()
                .map(m -> maintenanceConverter.toMaintenanceInstanceDTO(m.getId(), guest.getId()))
                .collect(toList()));
    }

    public Guest toGuest(GuestCreationDTO guestCreationDTO) {
        return new Guest(guestCreationDTO.getName(), guestCreationDTO.getPassport(),
                0, null,
                guestCreationDTO.getCheckInDate(), guestCreationDTO.getCheckOutDate(),
                null);
    }
}
