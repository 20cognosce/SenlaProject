package com.senla.controller;

import com.senla.controller.DTO.MaintenanceInstanceDTO;
import com.senla.controller.DTO.MaintenanceTemplateDTO;
import com.senla.controller.converters.MaintenanceConverter;
import com.senla.model.Guest;
import com.senla.model.Maintenance;
import com.senla.service.GuestService;
import com.senla.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping(value = "/maintenances", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    private final GuestService guestService;
    private final MaintenanceConverter converter;

    @GetMapping("/{id}")
    public MaintenanceTemplateDTO getMaintenanceById(@PathVariable("id") Long id) {
        Maintenance maintenance = maintenanceService.getById(id);
        return new MaintenanceTemplateDTO(maintenance);
    }

    @GetMapping
    public List<MaintenanceTemplateDTO> getAll(
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort,
            @RequestParam(value = "order", defaultValue = "asc", required = false) String order) {

        List<Maintenance> all = new ArrayList<>();

        if (Objects.equals(sort, "0")) {
            all = maintenanceService.sortByAddition(order);
        }
        if (Objects.equals(sort, "1")) {
            all = maintenanceService.sortByPrice(order);
        }
        if (Objects.equals(sort, "2")) {
            all = maintenanceService.sortByCategory(order);
        }
        return all.stream().map(converter::convert).collect(toList());
    }

    @GetMapping(params = {"guest_id"})
    public List<MaintenanceInstanceDTO> getMaintenancesOfGuestById(
            @RequestParam("guest_id") Long guestId,
            @RequestParam(value = "sort", defaultValue = "0", required = false) String sort,
            @RequestParam(value = "order", defaultValue = "asc", required = false) String order) {

        List<Maintenance> all = new ArrayList<>();

        if (Objects.equals(sort, "0")) {
            all = maintenanceService.sortMaintenancesOfGuestByAddition(guestId, order);
        }
        if (Objects.equals(sort, "1")) {
            all =  maintenanceService.sortMaintenancesOfGuestByPrice(guestId, order);
        }
        if (Objects.equals(sort, "2")) {
            all =  maintenanceService.sortMaintenancesOfGuestByTime(guestId, order);
        }
        return all.stream().map(m -> converter.toMaintenanceInstanceDTO(m.getId(), guestId)).collect(toList());
    }

    @PostMapping
    @ResponseBody
    public MaintenanceTemplateDTO createMaintenance(@RequestBody MaintenanceTemplateDTO maintenanceTemplateDTO) {
        Maintenance maintenance = converter.toMaintenance(maintenanceTemplateDTO);
        maintenanceService.createMaintenance(maintenance);
        return new MaintenanceTemplateDTO(maintenanceService.getById(maintenance.getId()));
    }

    @PatchMapping("/{id}/update")
    public MaintenanceTemplateDTO updateMaintenanceThroughDto(
            @PathVariable("id") Long id,
            @RequestBody MaintenanceTemplateDTO maintenanceTemplateDTO) {

        Maintenance detachedMaintenance = maintenanceService.getById(id);
        Maintenance updatedMaintenance = maintenanceService.updateEntityFromDto(detachedMaintenance, maintenanceTemplateDTO);

        maintenanceService.updateMaintenance(updatedMaintenance);
        return new MaintenanceTemplateDTO(maintenanceService.getById(id));
    }

    @PutMapping(value = "/{id}/order", params = {"guest_id"})
    public MaintenanceInstanceDTO orderMaintenance(@PathVariable("id") Long id, @RequestParam("guest_id") Long guestId) {
        Maintenance maintenance = maintenanceService.getById(id);
        Guest guest = guestService.getById(guestId);
        maintenanceService.executeMaintenance(guestId, id);
        return converter.toMaintenanceInstanceDTO(id, guestId);
    }
}
