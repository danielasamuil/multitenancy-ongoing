package com.multitenant.multitenancy.meta.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.multitenant.multitenancy.helpers.UrlMapping.ENTITY;
import static com.multitenant.multitenancy.helpers.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8091",  allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @PostMapping()
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @PutMapping(ENTITY)
    public UserDTO edit(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @DeleteMapping()
    public void deleteAll() {
        userService.deleteAll();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}


