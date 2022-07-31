package com.senla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "guest_2_maintenance")
public class Guest2Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long orderId;
    @Column(name = "guest_id")
    Long guestId;
    @Column(name = "maintenance_id")
    Long maintenanceId;
    @Column(name = "order_timestamp")
    LocalDateTime orderTime;

    public Guest2Maintenance(Long guestId, Long maintenanceId, LocalDateTime orderTime) {
        this.guestId = guestId;
        this.maintenanceId = maintenanceId;
        this.orderTime = orderTime;
    }
}
