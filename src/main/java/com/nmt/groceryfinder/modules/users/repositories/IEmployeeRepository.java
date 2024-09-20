package com.nmt.groceryfinder.modules.users.repositories;

import com.nmt.groceryfinder.modules.users.domain.model.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
    Optional<EmployeeEntity> findByUserEmail(String email);
}
