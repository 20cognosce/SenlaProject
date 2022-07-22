package com.senla.controller;

import com.senla.controller.DTO.GuestCreationDTO;
import com.senla.controller.DTO.GuestDTO;
import com.senla.model.Guest;
import com.senla.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/guests", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;
    private final Mapper mapper;

    @GetMapping("/{id}")
    public GuestDTO getById(@PathVariable("id") Long id) {
        Guest guest = guestService.getById(id);
        return mapper.toGuestDTO(guest);
    }

    @GetMapping("/{id}/payment")
    public Integer getGuestPaymentById(@PathVariable Long id) {
        return guestService.getPriceByGuest(id);
    }

    @GetMapping
    public List<GuestDTO> getAll(
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort) {

        List<Guest> all = new ArrayList<>();

        if (Objects.equals(sort, "0")) {
            all = guestService.sortByAddition();
        }
        if (Objects.equals(sort, "1")) {
            all = guestService.sortByAlphabet();
        }
        if (Objects.equals(sort, "2")) {
            all = guestService.sortByCheckOutDate();
        }
        return all.stream().map(mapper::toGuestDTO).collect(toList());
    }

    @GetMapping("/amount")
    public Long getAllAmount() {
        return guestService.getAllAmount();
    }

    @PostMapping("/new")
    @ResponseBody
    public GuestDTO createGuest(@RequestBody GuestCreationDTO guestCreationDTO) {
        Guest guest = mapper.toGuest(guestCreationDTO);
        guestService.createGuest(guest);
        return mapper.toGuestDTO(guestService.getById(guest.getId()));
    }

    @PostMapping(value = "/remove", params = {"guest_id"})
    @ResponseBody
    public ResponseEntity<?> removeGuest(@RequestParam("guest_id") Long guestId) {
        guestService.deleteGuest(guestId);
        try {
            guestService.getById(guestId);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.ok("Deleted successfully");
        }
        throw new IllegalStateException("Something went wrong, entity has not been deleted");
    }

    @PutMapping(value = "/{id}/add_to_room", params = {"room_id"})
    public  ResponseEntity<?> addGuestToRoom(
            @PathVariable("id") Long guestId,
            @RequestParam("room_id") Long roomId) {

        guestService.addGuestToRoom(guestId, roomId);
        if (guestService.getById(guestId).getRoom().getId() == roomId) {
            return ResponseEntity.ok("Successfully added to room");
        } else {
            throw new IllegalStateException("Something went wrong, guest's room differs");
        }
    }

    @PutMapping("/{id}/remove_from_room")
    public ResponseEntity<?> removeGuestFromRoom(
            @PathVariable("id") Long id) {

        guestService.removeGuestFromRoom(id);
        if (Objects.isNull(guestService.getById(id).getRoom())) {
            return ResponseEntity.ok("Successfully removed from room");
        } else {
            throw new IllegalStateException("Something went wrong, guest still has a room");
        }
    }
}
