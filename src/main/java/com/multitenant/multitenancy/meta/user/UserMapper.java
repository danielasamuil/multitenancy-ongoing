package com.multitenant.multitenancy.meta.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    User fromDto(UserDTO userDTO);
}

