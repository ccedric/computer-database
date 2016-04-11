package com.excilys.formation.java.computerdb.ui.util;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.ui.exception.WebservicePropertiesNotFound;
import com.excilys.formation.java.computerdb.ui.security.Authenticator;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 * Class containing the commands to communicate with the webservice wcompany.
 * 
 * @author Cédric Cousseran
 *
 */
public class CompanyWebServiceUtil {
  private static String apiUrl;
  private static final Client CLIENT = ClientBuilder.newBuilder()
      .register(new Authenticator("admin", "admin")).build();
  public static CompanyWebServiceUtil instance = new CompanyWebServiceUtil();

  private CompanyWebServiceUtil() {
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
   * List all companies with a webservice.
   */
  public List<CompanyDto> list() {
    WebTarget target = CLIENT.target(apiUrl + "company/list");
    Response response = target.request().get();
    System.out.println(response.getStatus());

    System.out.println(response.getEntity());

    List<CompanyDto> companies = response.readEntity(new GenericType<List<CompanyDto>>() {
    });
    response.close();
    return companies;
  }

  /**
   * Delete a Company with his id.
   */
  public void delete(long id) {
    WebTarget target = CLIENT.target(apiUrl + "company/delete/" + id);
    Response response = target.request().get();
    response.close();
  }

}
