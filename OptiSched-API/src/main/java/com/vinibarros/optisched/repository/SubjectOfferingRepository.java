package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.SubjectOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectOfferingRepository extends JpaRepository<SubjectOffering, Long> {
    List<SubjectOffering> findByCourseId(Long courseId);
    List<SubjectOffering> findBySubjectId(Long subjectId);
    List<SubjectOffering> findBySemesterId(Long semesterId);
    boolean existsByCourseIdAndSubjectIdAndSemesterIdAndSection(
            Long courseId,
            Long subjectId,
            Long semesterId,
            String section
    );
}
