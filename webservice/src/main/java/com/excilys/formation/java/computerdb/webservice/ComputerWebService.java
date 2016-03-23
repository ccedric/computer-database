package com.excilys.formation.java.computerdb.webservice;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.dto.mapper.ComputerDtoMapper;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.order.Column;
import com.excilys.formation.java.computerdb.order.Order;
import com.excilys.formation.java.computerdb.order.OrderSearch;
import com.excilys.formation.java.computerdb.service.ComputerService;
import com.excilys.formation.java.computerdb.service.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.ws.rs.core.Response;

/**
 * Webservice for the computer class.
 * 
 * @author Cédric Cousseran
 *
 */
@RestController
@RequestMapping("/api/computer")
public class ComputerWebService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerWebService.class);

  @Autowired
  private ComputerService computerService;

  @Autowired
  private ComputerDtoMapper computerDtoMapper;

  /**
   * List all computers.
   */
  @RequestMapping(value = "list", method = RequestMethod.GET)
  public List<ComputerDto> list() {
    Page page = new Page(1, 10, "");
    Column column = Column.computerId;
    Order order = Order.ASC;
    OrderSearch orderSearch = new OrderSearch(column, order);
    page.setOrderSearch(orderSearch);

    List<ComputerDto> computers = computerDtoMapper
        .listFromModel(computerService.listPageByName(page));
    
    return computers;
  }

  /**
   * Get a computer by an id.
   */
  @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
  public ComputerDto get(@PathVariable("id") long id) {
    ComputerDto computer = computerDtoMapper.fromModel(computerService.find(id));

    LOGGER.info("Computer retrieved with the id {}",id);
    return computer;
  }

  /**
   * Create a computer.
   */
  @RequestMapping(value = "create", method = RequestMethod.POST)
  public ComputerDto create(@RequestBody ComputerDto dto) {
    Computer computer = computerDtoMapper.toComputer(dto);
    computerService.create(computer);
    return computerDtoMapper.fromModel(computer);
  }
  
  /**
   * Update a computer.
   */
  @RequestMapping(value = "update", method = RequestMethod.POST)
  public ComputerDto update(@RequestBody ComputerDto dto) {
    Computer computer = computerDtoMapper.toComputer(dto);
    computerService.update(computer);
    return computerDtoMapper.fromModel(computer);
  }
  
  /**
   * Delete a computer.
   */
  @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
  public Response delete(@PathVariable("id") long id) {
    Computer computer = computerService.find(id);
    computerService.delete(computer);
    return Response.ok("ok").build();
  }
}
