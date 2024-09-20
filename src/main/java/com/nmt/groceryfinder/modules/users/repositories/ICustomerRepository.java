package com.nmt.groceryfinder.modules.users.repositories;

import com.nmt.groceryfinder.modules.users.domain.model.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerEntity, Integer> {
}
