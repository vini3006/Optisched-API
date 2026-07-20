package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
     boolean existsByIdAndInstitutionId(Long id, Long institutionId);
     List<Classroom> findAllByInstitutionId(Long institutionId);
     Optional<Classroom> findByIdAndInstitutionId(Long id, Long institutionId);
     boolean existsByNumberAndInstitutionId(String number, Long institutionId);
}
