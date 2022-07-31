package com.senla.controller;

import com.senla.controller.DTO.RoomCreationDTO;
import com.senla.controller.DTO.RoomDTO;
import com.senla.controller.converters.RoomConverter;
import com.senla.model.Room;
import com.senla.service.RoomService;
import com.senla.util.ParseDateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/rooms", produces = {"application/json; charset=UTF-8"})
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomConverter converter;

    @GetMapping("/{id}")
    public RoomDTO getById(@PathVariable Long id) {
        return converter.convert(roomService.getById(id));
    }

    @GetMapping("/{id}/details")
    public String getRoomDetails(@PathVariable Long id) {
        return roomService.getById(id).getDetails();
    }

    @GetMapping
    public List<RoomDTO> getAll(
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
            @RequestParam(value = "order", defaultValue = "asc", required = false) String order) {

        List<Room> all = new ArrayList<>();

        if (Objects.equals(sort, "id")) {
            all = roomService.sortByAddition(order);
        }
        if (Objects.equals(sort, "price")) {
            all = roomService.sortByPrice(order);
        }
        if (Objects.equals(sort, "stars")) {
            all = roomService.sortByStars(order);
        }
        if (Objects.equals(sort, "capacity")) {
            all = roomService.sortByCapacity(order);
        }
        return all.stream().map(converter::convert).collect(toList());
    }

    //http://localhost:8080/rooms/free?date=06.03.2022
    @GetMapping("/free")
    public List<RoomDTO> getAllFree(
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
            @RequestParam(value = "order", defaultValue = "asc", required = false) String order) {

        LocalDate dateToSearch = ParseDateUtil.getDateFromString(date);
        List<Room> all = new ArrayList<>();

        if (Objects.equals(sort, "id")) {
            all = roomService.sortFreeRoomsByAddition(dateToSearch, order);
        }
        if (Objects.equals(sort, "price")) {
            all = roomService.sortFreeRoomsByPrice(dateToSearch, order);
        }
        if (Objects.equals(sort, "stars")) {
            all = roomService.sortFreeRoomsByStars(dateToSearch, order);
        }
        if (Objects.equals(sort, "capacity")) {
            all = roomService.sortFreeRoomsByCapacity(dateToSearch, order);
        }
        return all.stream().map(converter::convert).collect(toList());
    }

    @GetMapping("/free/amount")
    public Long getFreeAmount(
            @RequestParam(value = "date", required = false) String date) {

        LocalDate dateToSearch = ParseDateUtil.getDateFromString(date);
        return roomService.getFreeAmount(dateToSearch);
    }

    @PostMapping
    public void createRoom(@RequestBody RoomCreationDTO roomCreationDTO) {
        Room room = converter.toRoom(roomCreationDTO);
        roomService.createRoom(room);
    }

    //another method for details because details are the not part of RoomDTO
    @PatchMapping(value = "/{id}", params = {"details"})
    public void updateRoomDetails(
            @PathVariable("id") Long id,
            @RequestParam("details") String details) {

        roomService.updateRoomDetails(id, details);
    }

    //for price and/or status
    @PatchMapping(value = "/{id}")
    public void updateRoomThroughDto(
            @PathVariable("id") Long id,
            @RequestBody RoomDTO roomDTO) {

        Room detachedRoom = roomService.getById(id);
        Room updatedRoom = roomService.updateEntityFromDto(detachedRoom, Room.class, roomDTO);

        try {
            roomService.updateRoom(updatedRoom);
        } catch (ServiceUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
