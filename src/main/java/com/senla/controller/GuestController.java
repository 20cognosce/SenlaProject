package com.senla.controller;

import com.senla.controller.DTO.GuestCreationDTO;
import com.senla.controller.DTO.GuestDTO;
import com.senla.controller.converters.GuestConverter;
import com.senla.model.Guest;
import com.senla.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/guests", produces = {"application/json; charset=UTF-8"})
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;
    private final GuestConverter converter;

    @GetMapping("/{id}")
    public GuestDTO getById(@PathVariable("id") Long id) {
        Guest guest = guestService.getById(id);
        return converter.convert(guest);
    }

    @GetMapping("/{id}/payment")
    public Integer getGuestPaymentById(@PathVariable Long id) {
        return guestService.getPriceByGuest(id);
    }

    @GetMapping
    public List<GuestDTO> getAll(
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
            @RequestParam(value = "order", defaultValue = "asc", required = false) String order) {

        List<Guest> all = new ArrayList<>();

        if (Objects.equals(sort, "id")) {
            all = guestService.sortByAddition(order);
        }
        if (Objects.equals(sort, "alphabet")) {
            all = guestService.sortByAlphabet(order);
        }
        if (Objects.equals(sort, "date")) {
            all = guestService.sortByCheckOutDate(order);
        }

        return all.stream().map(converter::convert).collect(toList());
    }

    @GetMapping("/amount")
    public Long getAllAmount() {
        return guestService.getAllAmount();
    }

    @PostMapping("/register")
    public void registerGuest(@RequestBody GuestCreationDTO guestCreationDTO) {
        Guest guest = converter.toGuest(guestCreationDTO);
        guestService.createGuest(guest);
        //так как login - not null unique в БД,
        //то при попытке создания пользователя с уже существующим логином EntityManager должен кинуть эксепшн
    }

    @DeleteMapping(value = "/{id}")
    public void removeGuest(@PathVariable("id") Long guestId) {
        guestService.deleteGuest(guestId);
    }

    @PutMapping(value = "/{id}/room", params = {"room_id", "check_in_date", "check_out_date"})
    public  void addGuestToRoom(
            @PathVariable("id") Long guestId,
            @RequestParam("room_id") Long roomId,
            @RequestParam("check_in_date") LocalDate checkInDate,
            @RequestParam("check_out_date") LocalDate checkOutDate) {

        guestService.addGuestToRoom(guestId, roomId, checkInDate, checkOutDate);
    }

    @DeleteMapping(value = "/{id}/room")
    public void removeGuestFromRoom(
            @PathVariable("id") Long id) {

        guestService.removeGuestFromRoom(id);
    }
}
