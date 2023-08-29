package com.multitenant.multitenancy.helpers;

import com.multitenant.multitenancy.config.security.AuthService;
import com.multitenant.multitenancy.config.security.dto.SignupRequest;
import com.multitenant.multitenancy.meta.user.ERole;
import com.multitenant.multitenancy.meta.user.Role;
import com.multitenant.multitenancy.meta.user.RoleRepository;
import com.multitenant.multitenancy.meta.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("admin@email.com")
                    .username("admin")
                    .password("admin")
                    .roles(Set.of("ADMIN"))
                    .build());
        }
    }
}
