package com.iunetworks.entities;

import com.iunetworks.superClasses.Account;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "physical_entity_accounts")
public class PhysicalEntityAccount extends Account {

}
