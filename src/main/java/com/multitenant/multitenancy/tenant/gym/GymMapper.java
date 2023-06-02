package com.multitenant.multitenancy.tenant.gym;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GymMapper {
    GymDTO toDTO(Gym gym);

    Gym fromDTO(GymDTO gymDTO);
}
