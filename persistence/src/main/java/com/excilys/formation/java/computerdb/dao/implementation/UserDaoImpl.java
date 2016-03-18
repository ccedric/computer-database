package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.model.User;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Dao for the model User.
 * 
 * @author Cédric Cousseran
 *
 */
@Repository
public class UserDaoImpl {

  @Autowired
  private SessionFactory sessionFactory;

  /**
   * Find a user with his username.
   */
  @SuppressWarnings("unchecked")
  public User find(String username) {
    Session session = sessionFactory.getCurrentSession();

    Criteria crit = session.createCriteria(User.class, "user")
        .add(Restrictions.eq("username", username));

    
    List<User> users = crit.list();
    return users.get(0);
  }
  
  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
