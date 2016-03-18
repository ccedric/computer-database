package com.excilys.formation.java.computerdb.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model for an User, used for the authentification.
 * 
 * @author Cédric Cousseran
 *
 */
@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @Column(name = "username", unique = true, nullable = false, length = 45)
  private String username;
  
  @Column(name = "password", length = 60)
  private String password;
  
  @Column(name = "enabled", nullable = false)
  private boolean enabled;
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private Set<UserRole> userRole = new HashSet<UserRole>(0);

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Set<UserRole> getUserRole() {
    return userRole;
  }

  public void setUserRole(Set<UserRole> userRole) {
    this.userRole = userRole;
  }
  
  
}
