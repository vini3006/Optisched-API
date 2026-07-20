package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    boolean existsByIdAndInstitutionId(Long id, Long institutionId);

    Optional<TimeSlot> findByIdAndInstitutionId(Long id, Long institutionId);

    boolean existsByDayOfWeekAndStartTimeAndEndTimeAndInstitutionId(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, Long institutionId);

    List<TimeSlot> findAllByInstitutionId(Long institutionId);

    List<TimeSlot> findByDayOfWeekAndInstitutionId(DayOfWeek dayOfWeek, Long institutionId);

    @Query("""
        SELECT COUNT(t) > 0
        FROM TimeSlot t
        WHERE t.institution.id = :institutionId
          AND :startTime < t.endTime
          AND :endTime > t.startTime
          AND NOT (t.startTime = :startTime AND t.endTime = :endTime)
    """)
    boolean existsOverlappingTimeSlot(
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("institutionId") Long institutionId
    );
}
