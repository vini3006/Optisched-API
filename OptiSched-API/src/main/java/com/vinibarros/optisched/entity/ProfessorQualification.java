package com.vinibarros.optisched.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "professor_qualification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorQualification {

    @EmbeddedId
    private ProfessorQualificationId id = new ProfessorQualificationId();

    @ManyToOne(optional = false)
    @MapsId("professorId")
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(optional = false)
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
