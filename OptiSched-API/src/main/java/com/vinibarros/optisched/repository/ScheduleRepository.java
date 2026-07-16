package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.Schedule;
import com.vinibarros.optisched.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsBySemesterIdAndStatus(Long semesterId, ScheduleStatus status);
    Schedule findBySemesterIdAndStatus(Long semesterId, ScheduleStatus status);
}
