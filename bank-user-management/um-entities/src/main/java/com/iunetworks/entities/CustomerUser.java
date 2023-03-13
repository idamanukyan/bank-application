package com.iunetworks.entities;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "t_customer_users")

public class CustomerUser extends User {

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "t_roles_customer_users",
    joinColumns = @JoinColumn(name = "customer_user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
