package com.senla.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "guest")
public class Guest extends AbstractEntity {

    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password")
    private String hashPassword;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "name")
    private String name;
    @Column(name = "passport")
    private String passport;
    @Column(name = "price")
    private int price;
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "guests")
    private List<Maintenance> orderedMaintenances = new ArrayList<>(); //while creating via builder null will be assigned
}
