package com.vinibarros.optisched.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "subject_offering",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"course_id", "subject_id", "semester_id", "section"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @Column(nullable = false)
    private String section;

    @Column(name = "expected_students", nullable = false)
    private Integer expectedStudents;

    @Column(name = "recommended_semester")
    private Integer recommendedSemester;

    @OneToMany(mappedBy = "subjectOffering")
    private Set<ScheduleEntry> scheduleEntries = new HashSet<>();
}
