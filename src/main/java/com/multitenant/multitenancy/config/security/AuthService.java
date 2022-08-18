package com.multitenant.multitenancy.config.security;

import com.multitenant.multitenancy.config.security.dto.SignupRequest;
import com.multitenant.multitenancy.meta.tenant.Tenant;
import com.multitenant.multitenancy.meta.tenant.TenantRepo;
import com.multitenant.multitenancy.meta.tenant.TenantState;
import com.multitenant.multitenancy.meta.tenant.pool.TenantPoolService;
import com.multitenant.multitenancy.meta.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    private final TenantRepo tenantRepo;

    private final TenantPoolService tenantPoolService;
    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {

        Tenant tenant = Tenant.builder()
                .dateCreated(LocalDate.now())
                .state(TenantState.ACTIVE)
                .version(1L)
                .schema(tenantPoolService.takeTenant())
                .build();

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .tenantID(tenant.getId())
                .build();

        Set<String> rolesStr = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.HELP_DESK)
                    .orElseThrow(() -> new RuntimeException("Cannot find HELP_DESK role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        tenantRepo.save(tenant);
    }
}