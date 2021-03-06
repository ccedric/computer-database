package com.excilys.formation.java.computerdb.ui.util;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.ui.exception.WebservicePropertiesNotFound;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;


/**
 * Class containing the commands to communicate with the computer webservice.
 * @author Cédric Cousseran
 *
 */
public final class ComputerWebServiceUtil {
  private static String apiUrl;
  private static final Client CLIENT = ClientBuilder.newBuilder().build();
  public static ComputerWebServiceUtil instance = new ComputerWebServiceUtil();

  private ComputerWebServiceUtil() {
    Properties prop = new Properties();
    try {
      prop.load(getClass().getClassLoader().getResourceAsStream("webservice.properties"));
      apiUrl = prop.getProperty("webservice.url");
    } catch (IOException e) {
      throw new WebservicePropertiesNotFound(
          "The properties file containing the webservice url wan't be opened", e);
    }
  }
  
  /**
   * Retrieve a ComputerDto from a webservice with his id.
   */
  public ComputerDto get(long id) {
    WebTarget target = CLIENT.target(apiUrl + "computer/get/" + id);
    Response response = target.request().get();
    ComputerDto computer = response.readEntity(ComputerDto.class);
    response.close();
    return computer;
  }
  
  /**
   * Retrieve a list of ComputerDto from a webservice.
   */
  public List<ComputerDto> list(Page pageComputer) {
    WebTarget target = CLIENT.target(apiUrl + "computer/list");
    Response response = target.request().post(Entity.entity(pageComputer, "application/json"));
    List<ComputerDto> computers = response.readEntity(new GenericType<List<ComputerDto>>() {
    });
    response.close();
    return computers;
  }
  
  /**
   * Create a ComputerDto with a webservice.
   */
  public ComputerDto create(ComputerDto computer) {
    WebTarget target = CLIENT.target(apiUrl + "computer/create");
    Response response = target.request().post(Entity.entity(computer, "application/json"));
    ComputerDto comp = response.readEntity(ComputerDto.class);
    response.close();
    return comp;
  }
  
  /**
   * Delete a computer with his webservice.
   */
  public void delete(long id) {
    WebTarget target = CLIENT.target(apiUrl + "computer/delete/" + id);
    Response response = target.request().get();
    response.close();
  }
  
  /**
   * Update a ComputerDto with a webservice.
   */
  public ComputerDto update(ComputerDto computer) {
    WebTarget target = CLIENT.target(apiUrl + "computer/update");
    Response response = target.request().post(Entity.entity(computer, "application/json"));
    ComputerDto comp = response.readEntity(ComputerDto.class);
    response.close();
    return comp;
  }
  
  
}
