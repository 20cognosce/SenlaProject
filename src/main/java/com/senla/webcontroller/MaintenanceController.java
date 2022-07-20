package com.senla.webcontroller;

import com.senla.model.Maintenance;
import com.senla.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/maintenances", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @GetMapping(params = {"id"})
    public Maintenance getMaintenanceById(@RequestParam("id") Long id) {
        return maintenanceService.getById(id);
    }

    @GetMapping
    public List<Maintenance> getAll(
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort) {

        if (Objects.equals(sort, "1")) {
            return maintenanceService.sortByPrice();
        }
        if (Objects.equals(sort, "2")) {
            return maintenanceService.sortByCategory();
        }
        return maintenanceService.sortByAddition();
    }

    @GetMapping(params = {"guest_id"})
    public List<Maintenance> getMaintenancesOfGuestById(
            @RequestParam("guest_id") Long guestId,
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort) {

        if (Objects.equals(sort, "1")) {
            return maintenanceService.sortMaintenancesOfGuestByPrice(guestId);
        }
        if (Objects.equals(sort, "2")) {
            return maintenanceService.sortMaintenancesOfGuestByTime(guestId);
        }
        return maintenanceService.sortMaintenancesOfGuestByAddition(guestId);
    }

    @PutMapping
    public ResponseEntity<?> createMaintenance(@RequestBody Maintenance maintenance) {
        maintenanceService.createMaintenance(maintenance);
        return ResponseEntity.ok("Maintenance created successfully");
    }

    /*@PatchMapping("/heavyresource/{id}")
    public ResponseEntity<?> partialUpdateName(
            @RequestBody HeavyResourceAddressOnly partialUpdate, @PathVariable("id") String id) {

        heavyResourceRepository.save(partialUpdate, id);
        return ResponseEntity.ok("resource address updated");
    }*/
}
