package com.multitenant.multitenancy;

import com.multitenant.multitenancy.config.security.AuthService;
import com.multitenant.multitenancy.config.security.dto.SignupRequest;
import com.multitenant.multitenancy.meta.tenant.Tenant;
import com.multitenant.multitenancy.meta.tenant.TenantRepo;
import com.multitenant.multitenancy.meta.tenant.TenantState;
import com.multitenant.multitenancy.meta.tenant.pool.TenantPoolService;
import com.multitenant.multitenancy.meta.user.User;
import com.multitenant.multitenancy.meta.user.UserDetailsServiceImpl;
import com.multitenant.multitenancy.meta.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MultiTenancyApplicationTests {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Test
  void contextLoads() {
    userDetailsService.
  }


}
