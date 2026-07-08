package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.Availability;
import com.vinibarros.optisched.entity.AvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, AvailabilityId> {
}
