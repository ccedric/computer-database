package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.dao.UserDao;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
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
public class UserDaoImpl implements UserDao {

  @Autowired
  private SessionFactory sessionFactory;

  /**
   * Find a user with his username.
   */
  @SuppressWarnings("unchecked")
  @Override
  public User find(String username) {
    Session session = sessionFactory.getCurrentSession();

    Criteria crit = session.createCriteria(User.class, "user")
        .add(Restrictions.eq("username", username));

    List<User> users = crit.list();
    return users.get(0);
  }
  
  @Override
  public User find(long id) {
    throw new NotImplementedException(
        "The method find for the dao User has not yet been implemented");

  }

  @Override
  public long create(User obj) {
    throw new NotImplementedException(
        "The method create for the dao User has not yet been implemented");
  }

  @Override
  public void delete(User obj) {
    throw new NotImplementedException(
        "The method delete for the dao User has not yet been implemented");
  }


  @Override
  public void update(User obj) {
    throw new NotImplementedException(
        "The method update for the dao User has not yet been implemented");
  }

  @Override
  public List<User> list() {
    throw new NotImplementedException(
        "The method list for the dao User has not yet been implemented");
  }
}
