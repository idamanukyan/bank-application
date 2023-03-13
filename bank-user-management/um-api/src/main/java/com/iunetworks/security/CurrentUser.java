package com.iunetworks.security;

import com.iunetworks.dtos.userdtos.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


public class CurrentUser implements UserDetails {

  private final UserPrincipal principal;


  public CurrentUser(UserPrincipal principal) {
    this.principal = principal;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.principal.
      authorities().stream()
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return this.principal.password();
  }

  @Override
  public String getUsername() {
    return this.principal.username();
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
}
