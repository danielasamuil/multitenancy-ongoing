package com.multitenant.multitenancy;

import com.multitenant.multitenancy.meta.user.User;
import com.multitenant.multitenancy.meta.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MultiTenancyApplicationTests {

  @Autowired
  private UserRepository userRepository;


  @Test
  void contextLoads() {
    userRepository.deleteAll();

    assertEquals(0, userRepository.count());

    User savedUser = userRepository.save(User.builder().email("email@1.com").username("username").password("123").tenantID(1).build());

    assertNotNull(savedUser);
    assertEquals(1, userRepository.count());

    assertTrue(userRepository.existsById(savedUser.getId()));

  }


}
