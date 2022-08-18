package com.multitenant.multitenancy.meta.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tenant")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Tenant {
  @Id
  @GeneratedValue
  private Integer id;

  private String schema;

  @Enumerated(EnumType.STRING)
  private TenantState state;

  @Version
  private Long version;

  @Column(name = "date_created")
  private LocalDate dateCreated;
}
