package com.senla.webcontroller;

import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import com.senla.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/guests", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GuestController {
    private final GuestService guestService;
    private final RoomService roomService;
    private final MaintenanceService maintenanceService;

    @GetMapping("/me")
    public Guest guest() {
        Guest guest = new Guest();
        guest.setName("Dmitry Chukov");
        return guest;
    }

    @GetMapping("/{id}")
    public Guest getById(@PathVariable Long id) {
        Guest guest = guestService.getById(id);
        //guest.setOrderedMaintenances(guestService.getGuestMaintenanceList(id));
        return guest;
    }

    @GetMapping("/{id}/payment")
    public Integer getGuestPaymentById(@PathVariable Long id) {
        return guestService.getPriceByGuest(id);
    }

    @GetMapping
    public List<Guest> getAll(
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort) {

        if (Objects.equals(sort, "1")) {
            return guestService.sortByAlphabet();
        }
        if (Objects.equals(sort, "2")) {
            return guestService.sortByCheckOutDate();
        }
        return guestService.sortByAddition();
    }
}
