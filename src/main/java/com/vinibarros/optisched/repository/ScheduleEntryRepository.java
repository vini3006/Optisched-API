package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.ScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleEntryRepository extends JpaRepository<ScheduleEntry, Long> {
}
