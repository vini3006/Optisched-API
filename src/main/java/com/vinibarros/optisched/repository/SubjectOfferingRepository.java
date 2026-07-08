package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.SubjectOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectOfferingRepository extends JpaRepository<SubjectOffering, Long> {
}
