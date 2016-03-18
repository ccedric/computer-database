package com.excilys.formation.java.computerdb.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model containing the role of an user.
 * @author Cédric Cousseran
 *
 */
@Entity
@Table(name = "user_role")
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  private String role;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
