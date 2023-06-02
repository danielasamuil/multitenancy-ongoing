package com.multitenant.multitenancy.tenant.staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "staff")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Staff {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(name = "staff_roles",
          joinColumns = @JoinColumn(name = "staff_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Builder.Default
  private Set<StaffRole> roles = new HashSet<>();

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 50)
  private String address;
}
