package com.senla.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "maintenance")
public class Maintenance extends AbstractEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private MaintenanceCategory category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "guest_2_maintenance",
            joinColumns = @JoinColumn(name = "maintenance_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    private List<Guest> guests = new ArrayList<>();
}
