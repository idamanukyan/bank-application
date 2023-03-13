package com.iunetworks.entities;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "t_bank_users")

public class BankUser extends User {

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "t_roles_bank_users",
    joinColumns = @JoinColumn(name = "bank_user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}

