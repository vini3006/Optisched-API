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
@Table(name = "semester")
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

    @OneToMany(mappedBy = "semester")
    private Set<SubjectOffering> offerings = new HashSet<>();

    @OneToMany(mappedBy = "semester")
    private Set<Schedule> schedules = new HashSet<>();
}
