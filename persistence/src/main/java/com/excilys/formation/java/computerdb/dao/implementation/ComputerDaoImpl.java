package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.dao.ComputerDao;
import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.exception.ComputerInvalidException;
import com.excilys.formation.java.computerdb.model.validation.ComputerValidator;
import com.excilys.formation.java.computerdb.order.OrderSearch;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for the class Computer.
 * 
 * @author Cédric Cousseran
 *
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {
  @Autowired
  private SessionFactory sessionFactory;

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

  /**
   * Delete all computers associated to the company.
   * 
   * @param obj
   *          the company
   */
  @SuppressWarnings("unchecked")
  @Override
  public void deleteByCompany(Company obj) {

    Session session = sessionFactory.getCurrentSession();

    Criteria crit = session.createCriteria(Computer.class, "computer")
        .add(Restrictions.like("company.id", obj.getId()));

    List<Computer> computers = crit.list();
    for (Computer computer : computers) {
      session.delete(computer);
      LOGGER.info("Computer deleted, id {}, name {}", computer.getId(), computer.getName());
    }
  }

  @Override
  public long create(Computer obj) {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while creating a new Computer, computer invalid ");
      throw new ComputerDaoInvalidException("Error while creating the coputer, computer invalid",
          e);
    }
    Session session = sessionFactory.getCurrentSession();
    long id = (long) session.save(obj);
    LOGGER.info("Computer created with name {}", obj.getName());
    return id;
  }

  @Override
  public void delete(Computer obj) {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while deleting the computer, computer invalid ");
      throw new ComputerDaoInvalidException("Error while deleting the computer, computer invalid",
          e);
    }
    Session session = sessionFactory.getCurrentSession();
    session.delete(obj);
    LOGGER.info("Computer deleted, id {}, name {}", obj.getId(), obj.getName());

  }

  @Override
  public void update(Computer obj) {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while updating the computer, computer invalid ");
      throw new ComputerDaoInvalidException("Error while updating the computer, computer invalid",
          e);
    }
    Session session = sessionFactory.getCurrentSession();
    session.merge(obj);
  }

  @Override
  public Computer find(long id) {
    Session session = sessionFactory.getCurrentSession();
    Computer computer = (Computer) session.get(Computer.class, id);

    if (computer == null) {
      LOGGER.warn("Computer not found with the id {}", id);
      throw new ComputerNotFoundException("Computer not found with the id ");
    } else {
      LOGGER.info(
          "Computer found, id {}, name {}, company {}, introduced date {}, discontinued date {}.",
          computer.getId(), computer.getName(), computer.getCompany(), computer.getIntroduced(),
          computer.getDiscontinued());
      return computer;
    }

  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Computer> list() {
    Session session = sessionFactory.getCurrentSession();

    List<Computer> computers = session.createCriteria(Computer.class, "computer")
        .createCriteria("company", "company", JoinType.LEFT_OUTER_JOIN).list();
    LOGGER.info("List of Computer found, size of the list: {}", computers.size());
    return computers;
  }

  @Override
  @SuppressWarnings({ "unchecked" })
  public List<Computer> listPageByName(int indexBegin, int pageSize, String name,
      OrderSearch order) {
    Session session = sessionFactory.getCurrentSession();
    String column;

    switch (order.getColumn().toString()) {
      case "computerId":
        column = "computer.id";
        break;
      case "computerName":
        column = "computer.name";
        break;
      case "companyName":
        column = "company.name";
        break;
      default:
        column = "computer.id";
        break;
    }

    Order orderQuery;
    if (order.getOrder() == com.excilys.formation.java.computerdb.order.Order.ASC) {
      orderQuery = Order.asc(column);
    } else {
      orderQuery = Order.desc(column);
    }

    Criteria crit;
    if (name == null || name.equals("")) {
      crit = session.createCriteria(Computer.class, "computer")
          .createCriteria("company", "company", JoinType.LEFT_OUTER_JOIN).addOrder(orderQuery)
          .setFirstResult(indexBegin).setMaxResults(indexBegin + pageSize);
    } else {
      crit = session.createCriteria(Computer.class, "computer")
          .createCriteria("company", "company", JoinType.LEFT_OUTER_JOIN).addOrder(orderQuery)
          .add(Restrictions.or(Restrictions.like("computer.name", name + '%'),
              Restrictions.like("company.name", name + '%')))
          .setFirstResult(indexBegin).setMaxResults(indexBegin + pageSize);
    }

    List<Computer> computers = crit.list();
    LOGGER.info("List of computers found, size of the list: {}", computers.size());
    return computers;
  }

  @Override
  public int selectCount(String name) {
    Session session = sessionFactory.getCurrentSession();

    Criteria crit = session.createCriteria(Computer.class, "computer")
        .createCriteria("company", "company", JoinType.LEFT_OUTER_JOIN)
        .add(Restrictions.or(Restrictions.like("computer.name", name + '%'),
            Restrictions.like("company.name", name + '%')))
        .setProjection(Projections.projectionList().add(Projections.property("id"), "id"));

    crit.setProjection(Projections.rowCount());

    int count = ((Long) crit.uniqueResult()).intValue();
    return count;
  }

}
