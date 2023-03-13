package com.iunetworks.util.admin;

import com.iunetworks.entities.BankUser;
import com.iunetworks.entities.Role;
import com.iunetworks.repositories.BankUserRepository;
import com.iunetworks.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class CreateAdmin {

  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final BankUserRepository bankUserRepository;


  public CreateAdmin(BankUserRepository bankUserRepository,
                     RoleRepository roleRepository,
                     PasswordEncoder passwordEncoder) {
    this.bankUserRepository = bankUserRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }


  public void createAdmin() {
    if (bankUserRepository.findByEmail("admin").isEmpty()) {

      BankUser bankUser = new BankUser();
      bankUser.setName("admin");
      bankUser.setSurname("admin");
      bankUser.setEmail("admin@gmail.com");
      bankUser.setPassword(passwordEncoder.encode("admin"));
      Role role = roleRepository.findAdminRole().get();
      bankUser.setRoles(Set.of(role));
      bankUser.setCreated(LocalDateTime.now());
      bankUserRepository.save(bankUser);

    }

  }

}
