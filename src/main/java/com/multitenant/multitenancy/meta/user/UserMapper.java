package com.multitenant.multitenancy.meta.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);
}

