package com.senla.controller;

import com.senla.controller.DTO.RoomCreationDTO;
import com.senla.controller.DTO.RoomDTO;
import com.senla.model.Room;
import com.senla.service.GuestService;
import com.senla.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RoomController {

    private final GuestService guestService;
    private final RoomService roomService;
    private final Mapper mapper;

    @GetMapping("/{id}")
    public Room getById(@PathVariable Long id) {
        return roomService.getById(id);
    }

    @GetMapping("/{id}/details")
    public String getRoomDetails(@PathVariable Long id) {
        return roomService.getById(id).getDetails();
    }

    @GetMapping
    public List<RoomDTO> getAll(
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort) {

        List<Room> all = new ArrayList<>();

        if (Objects.equals(sort, "0")) {
            all = roomService.sortByAddition();
        }
        if (Objects.equals(sort, "1")) {
            all = roomService.sortByPrice();
        }
        if (Objects.equals(sort, "2")) {
            all = roomService.sortByStars();
        }
        if (Objects.equals(sort, "3")) {
            all = roomService.sortByCapacity();
        }
        return all.stream().map(mapper::toRoomDto).collect(toList());
    }

    //http://localhost:8080/rooms/free?date=06.03.2022
    @GetMapping("/free")
    public List<RoomDTO> getAllFree(
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort,
            @RequestParam(value = "date", defaultValue = "now", required = false) String date) {

        LocalDate dateToSearch;

        if (Objects.equals(date, "now")) {
            dateToSearch = LocalDate.now();
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            dateToSearch = LocalDate.parse(date, dtf);
        }

        List<Room> all = new ArrayList<>();

        if (Objects.equals(sort, "0")) {
            all = roomService.sortFreeRoomsByAddition(dateToSearch);
        }
        if (Objects.equals(sort, "1")) {
            all = roomService.sortFreeRoomsByPrice(dateToSearch);
        }
        if (Objects.equals(sort, "2")) {
            all = roomService.sortFreeRoomsByStars(dateToSearch);
        }
        if (Objects.equals(sort, "3")) {
            all = roomService.sortFreeRoomsByCapacity(dateToSearch);
        }
        return all.stream().map(mapper::toRoomDto).collect(toList());
    }

    @GetMapping("/free/amount")
    public Long getFreeAmount(
            @RequestParam(value = "date", defaultValue = "now", required = false) String date) {

        LocalDate dateToSearch;

        if (Objects.equals(date, "now")) {
            dateToSearch = LocalDate.now();
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            dateToSearch = LocalDate.parse(date, dtf);
        }
        return roomService.getFreeAmount(dateToSearch);
    }

    @PostMapping("/new")
    @ResponseBody
    public RoomDTO createRoom(@RequestBody RoomCreationDTO roomCreationDTO) {
        Room room = mapper.toRoom(roomCreationDTO);
        roomService.createRoom(room);
        return mapper.toRoomDto(roomService.getById(room.getId()));
    }

    //another method for details because details are the not part of RoomDTO
    @PatchMapping(value = "/{id}/update", params = {"details"})
    public ResponseEntity<?> updateRoomDetails(
            @PathVariable("id") Long id,
            @RequestParam("details") String details) {

        roomService.updateRoomDetails(id, details);
        return ResponseEntity.ok("Room's details updated successfully");
    }

    //for price and/or status
    @PatchMapping("/{id}/update")
    public ResponseEntity<?> updateRoomThroughDto(
            @PathVariable("id") Long id,
            @RequestBody RoomDTO roomDTO) throws ServiceUnavailableException {

        Room detachedRoom = roomService.getById(id);
        Room updatedRoom = roomService.updateEntityFromDto(detachedRoom, roomDTO);

        roomService.updateRoom(updatedRoom);
        return ResponseEntity.ok("Room updated successfully");
    }
}
