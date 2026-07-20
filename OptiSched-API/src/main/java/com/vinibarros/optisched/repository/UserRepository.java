package com.vinibarros.optisched.repository;

import com.vinibarros.optisched.entity.User;
import com.vinibarros.optisched.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndInstitutionId(Long id, Long institutionId);

    boolean existsByIdAndInstitutionId(Long id, Long institutionId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndInstitutionId(String email, Long institutionId);

    List<User> findByInstitutionId(Long institutionId);

    List<User> findByInstitutionIdAndRole(Long institutionId, UserRole role);
}
