package com.senla.controller.converters;

import com.senla.controller.DTO.GuestCreationDTO;
import com.senla.controller.DTO.GuestDTO;
import com.senla.model.Guest;
import com.senla.model.Role;
import com.senla.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class GuestConverter implements Converter <Guest, GuestDTO> {

    private final MaintenanceService maintenanceService;
    private final MaintenanceConverter maintenanceConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GuestDTO convert(@NonNull Guest guest) {
        return new GuestDTO(guest, maintenanceService.sortMaintenancesOfGuestByAddition(guest.getId(), "asc")
                .stream()
                .map(m -> maintenanceConverter.toMaintenanceInstanceDTO(m.getId(), guest.getId()))
                .collect(toList()));
    }

    public Guest toGuest(GuestCreationDTO guestCreationDTO) {
        Role role;
        if ("admin".equals(guestCreationDTO.getLogin())) { //login уникален, поэтому будет только одна учетка админа
            role = Role.ADMIN;
        } else {
            role = Role.USER;
        }

        return Guest.builder()
                .login(guestCreationDTO.getLogin())
                .hashPassword(passwordEncoder.encode(guestCreationDTO.getPassword()))
                .role(role)
                .name(guestCreationDTO.getName())
                .passport(guestCreationDTO.getPassport())
                .price(0)
                .room(null)
                .checkInDate(null)
                .checkOutDate(null)
                .orderedMaintenances(null)
                .build();
    }
}
