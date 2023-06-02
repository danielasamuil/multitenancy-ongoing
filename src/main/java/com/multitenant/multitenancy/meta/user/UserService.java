package com.multitenant.multitenancy.meta.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserDTO> findAll() {

        List<UserDTO> users = userRepository.findAll().stream().map(
                        userMapper::toDTO)
                .collect(toList());

        return userRepository.findAll().stream().map(
                        userMapper::toDTO)
                .collect(toList());
    }

    public void delete(int id) {

        userRepository.deleteById(id);
    }

    public void deleteAll() {

        userRepository.deleteAll();
    }

    public UserDTO create(UserDTO userDTO) {

        Set<Role> roles = new HashSet<>();

        Role defaultRole = roleRepository.findByName(ERole.HELP_DESK)
                .orElseThrow(() -> new RuntimeException("Cannot find HELP_DESK role"));

        roles.add(defaultRole);

        User user = userMapper.fromDTO(userDTO);

        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setRoles(roles);
        user.setUsername(userDTO.getUsername());

        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO update(Integer id, UserDTO userDTO) {

        User user = findById(id);

        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUsername());

        return userMapper.toDTO(
                userRepository.save(user)
        );
    }
}

