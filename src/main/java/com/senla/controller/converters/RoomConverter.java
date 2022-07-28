package com.senla.controller.converters;

import com.senla.controller.DTO.RoomCreationDTO;
import com.senla.controller.DTO.RoomDTO;
import com.senla.model.Room;
import com.senla.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class RoomConverter implements Converter<Room, RoomDTO> {

    private final RoomService roomService;
    private final GuestConverter guestConverter;

    @Override
    public RoomDTO convert(@NonNull Room room) {
        return new RoomDTO(room, roomService.getGuestsList(room.getId())
                .stream()
                .map(guestConverter::convert)
                .collect(toList()));
    }

    public Room toRoom(RoomCreationDTO roomCreationDTO) {
        return new Room(roomCreationDTO.getName(), roomCreationDTO.getPrice(),
                roomCreationDTO.getCapacity(), roomCreationDTO.getStarsNumber(),
                roomCreationDTO.getRoomStatus());
    }
}
