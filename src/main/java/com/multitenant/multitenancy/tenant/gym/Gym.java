package com.multitenant.multitenancy.tenant.gym;

import com.multitenant.multitenancy.tenant.staff.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gym")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Gym {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false, length = 50)
    private String manager;
}
