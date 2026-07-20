package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.ProfessorQualification;
import com.vinibarros.optisched.entity.ProfessorQualificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorQualificationRepository extends JpaRepository<ProfessorQualification, ProfessorQualificationId> {
    boolean existsByIdAndInstitutionId(ProfessorQualificationId id, Long institutionId);

    List<ProfessorQualification> findAllByInstitutionId(Long institutionId);

    Optional<ProfessorQualification> findByIdAndInstitutionId(ProfessorQualificationId id, Long institutionId);

    List<ProfessorQualification> findById_ProfessorIdAndInstitutionId(Long professorId, Long institutionId);

    List<ProfessorQualification> findById_SubjectIdAndInstitutionId(Long subjectId, Long institutionId);

    List<ProfessorQualification> findById_ProfessorIdAndId_SubjectIdAndInstitutionId(Long professorId, Long subjectId, Long institutionId);
}
