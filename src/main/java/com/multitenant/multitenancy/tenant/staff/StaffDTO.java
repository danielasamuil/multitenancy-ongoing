package com.multitenant.multitenancy.tenant.staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private Integer id;
    private List<StaffRole> roles;
    private String name;
    private String address;
}
