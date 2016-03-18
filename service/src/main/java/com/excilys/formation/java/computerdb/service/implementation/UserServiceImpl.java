package com.excilys.formation.java.computerdb.service.implementation;

import com.excilys.formation.java.computerdb.dao.implementation.UserDaoImpl;
import com.excilys.formation.java.computerdb.model.UserRole;
import com.excilys.formation.java.computerdb.service.UserService;
import com.excilys.formation.java.computerdb.service.exception.ServiceNotImplementedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service for the dao user.
 * 
 * @author Cédric Cousseran
 *
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService, UserService {

  @Autowired
  private UserDaoImpl userDao;

  /**
   * return a UserDetails for the coresponding username.
   */
  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) {
    com.excilys.formation.java.computerdb.model.User user = userDao.find(username);
    List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

    return buildUserForAuthentication(user, authorities);
  }

  private User buildUserForAuthentication(com.excilys.formation.java.computerdb.model.User user,
      List<GrantedAuthority> authorities) {
    return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true,
        authorities);
  }

  private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
    Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

    // Build user's authorities
    for (UserRole userRole : userRoles) {
      setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
    }

    List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

    return result;
  }

  @Override
  public long create(com.excilys.formation.java.computerdb.model.User obj) {
    throw new ServiceNotImplementedException(
        "The method create is not implemented in the UserService");
  }

  @Override
  public void delete(com.excilys.formation.java.computerdb.model.User obj) {
    throw new ServiceNotImplementedException(
        "The method delete is not implemented in the UserService");
  }

  @Override
  public void update(com.excilys.formation.java.computerdb.model.User obj) {
    throw new ServiceNotImplementedException(
        "The method update is not implemented in the UserService");
  }

  @Override
  public com.excilys.formation.java.computerdb.model.User find(long id) {
    throw new ServiceNotImplementedException(
        "The method find is not implemented in the UserService");
  }

  @Override
  public List<com.excilys.formation.java.computerdb.model.User> list() {
    throw new ServiceNotImplementedException(
        "The method list is not implemented in the UserService");
  }

}
