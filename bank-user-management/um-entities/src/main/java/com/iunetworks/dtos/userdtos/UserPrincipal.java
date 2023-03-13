package com.iunetworks.dtos.userdtos;

import java.util.Set;
import java.util.UUID;

public interface UserPrincipal {

  Set<String> authorities();

  String username();

  String password();


}
