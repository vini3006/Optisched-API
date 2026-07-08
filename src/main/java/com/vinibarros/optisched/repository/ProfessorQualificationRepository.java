package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.ProfessorQualification;
import com.vinibarros.optisched.entity.ProfessorQualificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorQualificationRepository extends JpaRepository<ProfessorQualification, ProfessorQualificationId> {
}
