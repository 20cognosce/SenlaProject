package com.senla.controller;

import com.senla.controller.DTO.GuestCreationDTO;
import com.senla.controller.DTO.GuestDTO;
import com.senla.controller.converters.GuestConverter;
import com.senla.model.Guest;
import com.senla.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/guests", produces = MediaType.APPLICATION_JSON_VALUE)
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
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort,
            @RequestParam(value = "order", defaultValue = "asc", required = false) String order) {

        List<Guest> all = new ArrayList<>();

        if (Objects.equals(sort, "0")) {
            all = guestService.sortByAddition(order);
        }
        if (Objects.equals(sort, "1")) {
            all = guestService.sortByAlphabet(order);
        }
        if (Objects.equals(sort, "2")) {
            all = guestService.sortByCheckOutDate(order);
        }

        return all.stream().map(converter::convert).collect(toList());
    }

    @GetMapping("/amount")
    public Long getAllAmount() {
        return guestService.getAllAmount();
    }

    @PostMapping
    @ResponseBody
    public GuestDTO createGuest(@RequestBody GuestCreationDTO guestCreationDTO) {
        Guest guest = converter.toGuest(guestCreationDTO);
        guestService.createGuest(guest);
        return converter.convert(guestService.getById(guest.getId()));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public ResponseEntity<?> removeGuest(@PathVariable("id") Long guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.ok("Гость удален успешно");
    }

    @PutMapping(value = "/{id}/room", params = {"room_id"}, produces = {"application/json; charset=UTF-8"})
    public  ResponseEntity<?> addGuestToRoom(
            @PathVariable("id") Long guestId,
            @RequestParam("room_id") Long roomId) {

        guestService.addGuestToRoom(guestId, roomId);
        return ResponseEntity.ok("Гость успешно добавлен в комнату");
    }

    @DeleteMapping(value = "/{id}/room", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> removeGuestFromRoom(
            @PathVariable("id") Long id) {

        guestService.removeGuestFromRoom(id);
        return ResponseEntity.ok("Гость успешно удалён из комнаты");
    }
}
