package com.excilys.formation.java.computerdb.ui.util;

import com.excilys.formation.java.computerdb.dto.CompanyDto;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;


/**
 * Class containing the commands to communicate with the webservice wcompany.
 * @author Cédric Cousseran
 *
 */
public final class CompanyWebServiceUtil {
  private static final String API_URL = "http://localhost:8081/computerDB/api/";
  private static final Client CLIENT = ClientBuilder.newBuilder().build();

  /**
   * List all companies with a webservice.
   */
  public List<CompanyDto> list() {
    WebTarget target = CLIENT.target(API_URL + "company/list");
    Response response = target.request().get();
    List<CompanyDto> companies = response.readEntity(new GenericType<List<CompanyDto>>() {
    });
    response.close();
    return companies;
  }
  
  /**
   * Delete a Company with his id.
   */
  public void delete(long id) {
    WebTarget target = CLIENT.target(API_URL + "company/delete/" + id);
    Response response = target.request().get();
    response.close();
  }

}
