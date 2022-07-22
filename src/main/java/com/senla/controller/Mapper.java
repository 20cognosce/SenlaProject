package com.senla.controller;

import com.senla.controller.DTO.GuestCreationDTO;
import com.senla.controller.DTO.GuestDTO;
import com.senla.controller.DTO.MaintenanceInstanceDTO;
import com.senla.controller.DTO.MaintenanceTemplateDTO;
import com.senla.controller.DTO.RoomCreationDTO;
import com.senla.controller.DTO.RoomDTO;
import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.model.Room;
import com.senla.service.GuestService;
import com.senla.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
class Mapper {
    private final GuestService guestService;
    private final RoomService roomService;

    /*GUEST*/
    public GuestDTO toGuestDTO(Guest guest) {
        return new GuestDTO(guest, guestService.getGuestMaintenanceList(guest.getId())
                .stream()
                .map(this::toMaintenanceInstanceDTO)
                .collect(toList()));
    }

    public Guest toGuest(GuestCreationDTO guestCreationDTO) {
        return new Guest(guestCreationDTO.getName(), guestCreationDTO.getPassport(),
                0, null,
                guestCreationDTO.getCheckInDate(), guestCreationDTO.getCheckOutDate(),
                null);
    }

    /*ROOM*/
    public RoomDTO toRoomDto(Room room) {
        return new RoomDTO(room, roomService.getGuestsList(room.getId())
                .stream()
                .map(this::toGuestDTO)
                .collect(toList()));
    }

    public Room toRoom(RoomCreationDTO roomCreationDTO) {
        return new Room(roomCreationDTO.getName(), roomCreationDTO.getPrice(),
                roomCreationDTO.getCapacity(), roomCreationDTO.getStarsNumber(),
                roomCreationDTO.getRoomStatus());
    }


    /*Maintenance*/
    public MaintenanceTemplateDTO toMaintenanceTemplateDTO(Maintenance maintenance) {
        return new MaintenanceTemplateDTO(maintenance);
    }

    public MaintenanceInstanceDTO toMaintenanceInstanceDTO(Maintenance maintenance) {
        return new MaintenanceInstanceDTO(maintenance);
    }

    public Maintenance toMaintenance(MaintenanceTemplateDTO maintenanceTemplateDTO) {
        return new Maintenance(maintenanceTemplateDTO.getName(),
                maintenanceTemplateDTO.getPrice(),
                maintenanceTemplateDTO.getCategory(),
                null, null);
    }

    public Maintenance toMaintenance(MaintenanceInstanceDTO maintenanceInstanceDTO) {
        return new Maintenance(maintenanceInstanceDTO.getName(),
                maintenanceInstanceDTO.getPrice(),
                maintenanceInstanceDTO.getCategory(),
                guestService.getById(maintenanceInstanceDTO.getGuestId()),
                maintenanceInstanceDTO.getOrderTime());
    }
}
