package com.multitenant.multitenancy.tenant.staff;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    StaffDTO toDTO(Staff staff);

    Staff fromDTO(StaffDTO staffDTO);
}
