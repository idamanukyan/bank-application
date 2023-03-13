package com.iunetworks.dtos.userdtos;

import com.iunetworks.entities.Permission;
import com.iunetworks.entities.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomerUserDto implements UserPrincipal {

  private UUID id;
  @NotEmpty
  @NotNull
  private String name;
  @NotEmpty
  @NotNull
  private String surname;
  @NotEmpty
  @NotNull
  @Email(message = "Email is wrong")
  private String email;
  @NotEmpty
  @NotNull
  private String password;
  @NotEmpty
  @NotNull
  private String rePassword;

  private LocalDateTime deleted;

  private Set<Role> roles;


  public CustomerUserDto() {
  }

  public CustomerUserDto(UUID id, String name, String surname, String email, String password, String rePassword, Boolean softDeleted, LocalDateTime deleted, Set<Role> roles) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.password = password;
    this.rePassword = rePassword;
    this.deleted = deleted;
    this.roles = roles;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRePassword() {
    return rePassword;
  }

  public void setRePassword(String rePassword) {
    this.rePassword = rePassword;
  }

  public LocalDateTime getDeleted() {
    return deleted;
  }

  public void setDeleted(LocalDateTime deleted) {
    this.deleted = deleted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CustomerUserDto userDto = (CustomerUserDto) o;
    return Objects.equals(name, userDto.name) &&
      Objects.equals(surname, userDto.surname) &&
      Objects.equals(email, userDto.email) &&
      Objects.equals(password, userDto.password) &&
      Objects.equals(rePassword, userDto.rePassword);
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }


  @Override
  public int hashCode() {
    return Objects.hash(name, surname, email, password, rePassword);
  }

  @Override
  public String toString() {
    return "UserDto{" +
      "name='" + name + '\'' +
      ", surname='" + surname + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      ", rePassword='" + rePassword + '\'' +
      '}';
  }

  @Override
  public Set<String> authorities() {
    final Set<String> permissions = new HashSet<>();

    this.roles.forEach(r -> permissions.addAll(
      r.getPermissions().stream().map(Permission::getName).collect(Collectors.toList())
    ));

    return permissions;
  }

  @Override
  public String username() {
    return this.email;
  }

  @Override
  public String password() {
    return this.password;
  }
}
