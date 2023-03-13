package com.iunetworks.bootstrap;

import com.iunetworks.entities.Permission;
import com.iunetworks.entities.Role;
import com.iunetworks.repositories.PermissionRepository;
import com.iunetworks.repositories.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserManagementApplicationBootstrap {

  private static final String admin_role = "ADMIN";
  private static final String customer_role = "CUSTOMER";

  private final RoleRepository repository;
  private final PermissionRepository permissionRepository;

  public UserManagementApplicationBootstrap(final RoleRepository repository,
                                            final PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
    this.repository = repository;
  }

  public void init() {

    if (!this.repository.existsByName(admin_role)) {
      Role adminRole = new Role();
      adminRole.setName(admin_role);
      adminRole.setPermissions(this.adminPermissions());
      this.repository.save(adminRole);
    }
    if (!this.repository.existsByName(customer_role)) {
      Role customerRole = new Role();
      customerRole.setName(customer_role);
      customerRole.setPermissions(this.customerPermissions());
      this.repository.save(customerRole);
    }
  }

  private Set<Permission> adminPermissions() {
    return this.permissionRepository.findAllByNameIn(
      Set.of("can_add_admin", "can_delete_admin", "can_view_admin", "can_update_admin",
        "can_add_customer", "can_delete_customer", "can_update_customer")
    );
  }


  private Set<Permission> customerPermissions() {
    return this.permissionRepository.findAllByNameIn(
      Set.of("can_add_customer", "can_view_customer", "can_update_customer")
    );
  }


}
