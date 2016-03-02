package com.excilys.formation.java.computerdb.service.implementation;

import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDao;
import com.excilys.formation.java.computerdb.db.TransactionManager;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.service.exception.TimestampDiscontinuedBeforeIntroducedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for the model Computer, call the DAO Computer.
 * 
 * @author Cédric Cousseran
 */
@Service
@Transactional
public class ComputerService
    implements com.excilys.formation.java.computerdb.service.Service<Computer> {
  @Autowired
  ComputerDao computerDao;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

  public ComputerDao getComputerDao() {
    return computerDao;
  }

  public void setComputerDao(ComputerDao computerDao) {
    this.computerDao = computerDao;
  }

  @Override
  @Transactional(readOnly = false)
  public int create(Computer obj)
      throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException {
    if ((obj.getIntroduced() != null) && (obj.getDiscontinued() != null)
        && (obj.getIntroduced().isAfter(obj.getDiscontinued()))) {
      throw new TimestampDiscontinuedBeforeIntroducedException(
          "The discontinued timestamp is before the introduced timestamp");
    }
    try {
      return computerDao.create(obj);
    } catch (ComputerDaoInvalidException | DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService creation");
    } finally {
      TransactionManager.getInstance().remove();
    }
    return 0;
  }

  @Override
  @Transactional(readOnly = false)
  public void delete(Computer obj) throws DatabaseConnectionException {
    try {
      computerDao.delete(obj);
    } catch (ComputerNotFoundException | DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService delete");
    } finally {
      TransactionManager.getInstance().remove();
    }
  }

  @Override
  @Transactional(readOnly = false)
  public void update(Computer obj)
      throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException {
    if ((obj.getIntroduced() != null) && (obj.getDiscontinued() != null)
        && (obj.getIntroduced().isAfter(obj.getDiscontinued()))) {
      throw new TimestampDiscontinuedBeforeIntroducedException(
          "The discontinued timestamp is before the introduced timestamp");
    }
    try {
      computerDao.update(obj);
    } catch (ComputerNotFoundException | DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService update");
    } finally {
      TransactionManager.getInstance().remove();
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Computer find(long id) throws DatabaseConnectionException {
    try {
      return computerDao.find(id);
    } catch (DaoSqlException | ComputerNotFoundException e) {
      LOGGER.info("Exception catched in the ComputerService find");
    } finally {
      TransactionManager.getInstance().remove();
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> list() throws DatabaseConnectionException {
    try {
      return computerDao.list();
    } catch (DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService list");
    } finally {
      TransactionManager.getInstance().remove();
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> listPage(Page page) throws DatabaseConnectionException {
    try {
      return computerDao.listPage(page.getStartingIndex(), page.getPageSize());
    } catch (DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService listPage");
    } finally {
      TransactionManager.getInstance().remove();
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> findByName(String name) throws DatabaseConnectionException {
    try {
      return computerDao.findByName(name);
    } catch (DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService findByName");
    } finally {
      TransactionManager.getInstance().remove();
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> listPageByName(Page page) throws DatabaseConnectionException {
    try {
      return computerDao.listPageByName(page.getStartingIndex(), page.getPageSize(),
          page.getSearch(), page.getOrderSearch());
    } catch (DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService listPageByName");
    } finally {
      TransactionManager.getInstance().remove();
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public int selectCount(String name) {
    try {
      return computerDao.selectCount(name);
    } catch (DatabaseConnectionException | DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService selectCount");
    } finally {
      TransactionManager.getInstance().remove();
    }
    return 0;
  }

}
