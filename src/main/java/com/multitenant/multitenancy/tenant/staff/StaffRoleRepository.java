package com.multitenant.multitenancy.tenant.staff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRoleRepository extends JpaRepository<StaffRole, Integer> {
    Optional<StaffRole> findByName(StaffERole role);
}
