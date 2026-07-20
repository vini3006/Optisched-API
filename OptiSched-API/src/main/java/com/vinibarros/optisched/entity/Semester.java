package com.vinibarros.optisched.entity;
import com.vinibarros.optisched.enums.Term;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "semester", uniqueConstraints = {
        @UniqueConstraint(name = "unique_semester_per_institution", columnNames = {"institution_id", "year", "term"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Term term;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    @OneToMany(mappedBy = "semester")
    private Set<SubjectOffering> offerings = new HashSet<>();

    @OneToMany(mappedBy = "semester")
    private Set<Schedule> schedules = new HashSet<>();
}
