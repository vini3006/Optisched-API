package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.Schedule;
import com.vinibarros.optisched.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByIdAndInstitutionId(Long id, Long institutionId);

    Optional<Schedule> findByIdAndInstitutionId(Long id, Long institutionId);

    List<Schedule> findAllByInstitutionId(Long institutionId);

    boolean existsBySemesterIdAndStatusAndInstitutionId(Long semesterId, ScheduleStatus status, Long institutionId);

    Schedule findBySemesterIdAndStatusAndInstitutionId(Long semesterId, ScheduleStatus status, Long institutionId);
}
