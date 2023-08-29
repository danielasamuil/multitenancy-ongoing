package com.multitenant.multitenancy.meta.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multitenant.multitenancy.meta.tenant.Tenant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
  private static final Integer serialVersionUID = 1;

  private final Integer id;

  private final String username;

  private final String email;

  @JsonIgnore
  private final String password;

  private final String tenantID;

  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Integer id, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities, String tenantID) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.tenantID = tenantID;
  }

  public static UserDetailsImpl build(User user, Tenant tenant) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        authorities,
            tenant.getSchema());
  }

  public List<User> findAll(UserRepository userRepository){
    return userRepository.findAll();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
