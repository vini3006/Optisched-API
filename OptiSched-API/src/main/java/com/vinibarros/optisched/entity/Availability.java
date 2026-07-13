package com.vinibarros.optisched.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "availability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Availability {

    @EmbeddedId
    private AvailabilityId id = new AvailabilityId();

    @ManyToOne(optional = false)
    @MapsId("professorId")
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(optional = false)
    @MapsId("timeSlotId")
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;
}
