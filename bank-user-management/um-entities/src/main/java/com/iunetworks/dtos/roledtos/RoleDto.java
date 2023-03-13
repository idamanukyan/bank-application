package com.iunetworks.dtos.roledtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


public class RoleDto {

  private UUID id;
  @NotEmpty
  @NotNull
  private String name;
  @NotEmpty
  @NotNull
  private String description;

  public RoleDto() {
  }

  public RoleDto(UUID id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
