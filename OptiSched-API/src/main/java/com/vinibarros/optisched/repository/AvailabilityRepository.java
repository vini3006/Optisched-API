package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.Availability;
import com.vinibarros.optisched.entity.AvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, AvailabilityId> {
    boolean existsByIdAndInstitutionId(AvailabilityId id, Long institutionId);
    Optional<Availability> findByIdAndInstitutionId(AvailabilityId id, Long institutionId);
    List<Availability> findById_ProfessorIdAndInstitutionId(Long professorId, Long InstitutionId);
    List<Availability> findById_TimeSlotIdAndInstitutionId(Long timeSlotId, Long InstitutionId);
    List<Availability> findById_ProfessorIdAndId_TimeSlotIdAndInstitutionId(Long professorId, Long timeSlotId, Long institutionId);
    List<Availability> findAllByInstitutionId(Long institutionId);
}
