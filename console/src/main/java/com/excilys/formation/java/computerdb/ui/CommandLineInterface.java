package com.excilys.formation.java.computerdb.ui;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.ui.util.CompanyWebServiceUtil;
import com.excilys.formation.java.computerdb.ui.util.ComputerWebServiceUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Create a command line interface to use the application.
 * 
 * @author Cédric Cousseran
 *
 */
public class CommandLineInterface {
  private static Scanner sc = new Scanner(System.in);
  private static int pageComputerSize = 10;

  private static ComputerWebServiceUtil webserviceComputer = new ComputerWebServiceUtil();
  private static CompanyWebServiceUtil webserviceCompany = new CompanyWebServiceUtil();

  /**
   * Main of the application for the command line interface.
   * 
   * @param args
   *          Currently not used
   */
  public static void main(String[] args) {
    new CommandLineInterface().init();
  }

  private void init() {
    System.out.println("------------------------------------------");
    System.out.println("---Welcome to the computer database app---");
    System.out.println("------------------------------------------");

    showHelp();

  }

  /**
   * Show the help menu.
   */
  private void showHelp() {
    System.out.println();
    System.out.println("What do you want to do:");
    System.out.println("1: List computers");
    System.out.println("2: List companies");
    System.out.println("3: Show a computer details");
    System.out.println("4: Create a computer");
    System.out.println("5: Update a computer");
    System.out.println("6: Delete a computer");
    System.out.println("7: Delete a company");
    System.out.println("8: Exit the application");

    String input = sc.nextLine();
    switch (input) {
      case "1":
        listComputer();
        break;
      case "2":
        listCompanies();
        break;
      case "3":
        showComputerDetails();
        break;
      case "4":
        createComputer();
        break;
      case "5":
        updateComputer();
        break;
      case "6":
        deleteComputer();
        break;
      case "7":
        deleteCompany();
        break;
      case "8":
        System.out.println("Good bye");
        sc.close();
        System.exit(0);
        break;
      default:
        System.out.println("Input incorrect");
        showHelp();
    }
  }

  /**
   * CLI to delete a computer.
   */
  private void deleteComputer() {
    System.out.println(
        "Please enter the id of the computer you want to delete, or type q to go back to the menu");
    String input = sc.nextLine();
    if (input.equals("q")) {
      showHelp();
    }
    try {
      int id = Integer.parseInt(input);
      webserviceComputer.delete(id);
      System.out.println("Computer successfully deleted");

      showHelp();
    } catch (NumberFormatException e) {
      System.out.println("The id you entered is not a number, please type it again");
      showComputerDetails();
    }
  }

  /**
   * CLI to delete a company.
   */
  private void deleteCompany() {
    System.out.println(
        "Please enter the id of the company you want to delete, or type q to go back to the menu");
    String input = sc.nextLine();
    if (input.equals("q")) {
      showHelp();
    }
    try {
      int id = Integer.parseInt(input);
      webserviceCompany.delete(id);
      //      if (response.getStatus() == 200) {
      //        System.out.println("Company and associated computers successfully deleted");
      //      } else {
      //        System.out.println("Error while deleting a company");
      //      }
      showHelp();
    } catch (NumberFormatException e) {
      System.out.println("The id you entered is not a number, please type it again");
      showComputerDetails();
    }
  }

  /**
   * CLI to update the computer.
   */
  private void updateComputer() {
    System.out.println(
        "Please enter the id of the computer you want to update, or type q to go back to the menu");
    String inputId = sc.nextLine();
    if (inputId.equals("q")) {
      showHelp();
    }
    ComputerDto comp = null;
    try {
      int id = Integer.parseInt(inputId);
      
      comp = webserviceComputer.get(id);
      if (comp.getId() != 0) {
        System.out.println("Computer selected:");
        System.out.println(comp);
      } else {
        System.out.println("This computer doesn't exist");
        updateComputer();
      }
    } catch (NumberFormatException e) {
      System.out.println("The id you entered is not a number, please type it again");
      updateComputer();
    }

    System.out
        .println("Please enter the new name of the computer, or type q to go back to the menu");
    String name = sc.nextLine();
    if (name.equals("q")) {
      showHelp();
    }
    
    int idManufacturer = 0;
    while (true) {
      try {
        System.out.println(
            "Please enter the id of the manufacturer of the computer, type 0 if you don't want"
                + " to add this value, or type q to go back to the menu");
        String input = sc.nextLine();
        if (input.equals("q")) {
          showHelp();
        }
        if (input.equals("0")) {
          break;
        }
        idManufacturer = Integer.parseInt(input);
        break;
      } catch (NumberFormatException e) {
        System.out.println("The id you entered is not a number, please type it again");
        showComputerDetails();
      }
    }

    String introduced = new String();
    while (true) {
      System.out.println(
          "Please enter the introduced timestamp, format: MM-dd-yyyy, type 0 if you don't want"
              + " to add this value, or type q to go back to the menu");
      String input = sc.nextLine();
      if (input.equals("q")) {
        showHelp();
      }
      if (input.equals("0")) {
        break;
      }
      try {
        introduced = input;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        try {
          LocalDate.parse(introduced, formatter);
          break;
        } catch (Exception e) {
          System.out.println("The introduced date must be mm-dd-yyyy");
        }

      } catch (DateTimeParseException e) {
        System.out.println("The timestamp doesn't have the right format.");
      }
    }

    String discontinued = new String();
    while (true) {
      System.out.println(
          "Please enter the discontinued timestamp, format: MM-dd-yyyy, or type 0 if you don't"
              + " want to add this value, or type q to go back to the menu");
      String input = sc.nextLine();
      if (input.equals("q")) {
        showHelp();
      }
      if (input.equals("0")) {
        break;
      }
      try {
        discontinued = input;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        try {
          LocalDate.parse(discontinued, formatter);
          break;
        } catch (Exception e) {
          System.out.println("The introduced date must be mm-dd-yyyy");
        }
      } catch (DateTimeParseException e) {
        System.out.println("The timestamp doesn't have the right format.");
      }
    }

    comp = new ComputerDto.ComputerDtoBuilder(name)
        .companyId(idManufacturer).introduced(introduced).discontinued(discontinued).build();

    ComputerDto computer = webserviceComputer.update(comp);
    System.out.println("Computer update successfully");
    System.out.println(computer);
    showHelp();
  }

  /**
   * CLI to create a computer.
   */
  private void createComputer() {
    System.out.println("Please enter the name of the computer you want to create,"
        + " or type q to go back to the menu");
    String name = sc.nextLine();
    if (name.equals("q")) {
      showHelp();
    }

    int idManufacturer = 0;
    while (true) {
      try {
        System.out.println(
            "Please enter the id of the manufacturer of the computer, type 0 if you don't want"
                + " to add this value, or type q to go back to the menu");
        String input = sc.nextLine();
        if (input.equals("q")) {
          showHelp();
        }
        if (input.equals("0")) {
          break;
        }
        idManufacturer = Integer.parseInt(input);
        break;
      } catch (NumberFormatException e) {
        System.out.println("The id you entered is not a number, please type it again");
        showComputerDetails();
      }
    }

    String introduced = new String();
    while (true) {
      System.out.println(
          "Please enter the introduced timestamp, format: MM-dd-yyyy, type 0 if you don't want"
              + " to add this value, or type q to go back to the menu");
      String input = sc.nextLine();
      if (input.equals("q")) {
        showHelp();
      }
      if (input.equals("0")) {
        break;
      }
      try {
        introduced = input;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        try {
          LocalDate.parse(introduced, formatter);
          break;
        } catch (Exception e) {
          System.out.println("The introduced date must be mm-dd-yyyy");
        }

      } catch (DateTimeParseException e) {
        System.out.println("The timestamp doesn't have the right format.");
      }
    }

    String discontinued = new String();
    while (true) {
      System.out.println(
          "Please enter the discontinued timestamp, format: MM-dd-yyyy, or type 0 if you don't"
              + " want to add this value, or type q to go back to the menu");
      String input = sc.nextLine();
      if (input.equals("q")) {
        showHelp();
      }
      if (input.equals("0")) {
        break;
      }
      try {
        discontinued = input;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        try {
          LocalDate.parse(discontinued, formatter);
          break;
        } catch (Exception e) {
          System.out.println("The introduced date must be mm-dd-yyyy");
        }
      } catch (DateTimeParseException e) {
        System.out.println("The timestamp doesn't have the right format.");
      }
    }

    ComputerDto comp = new ComputerDto.ComputerDtoBuilder(name).companyId(idManufacturer)
        .introduced(introduced).discontinued(discontinued).build();

    comp = webserviceComputer.create(comp);
    if (comp.getId() != 0) {
      System.out.println("Computer created successfully");
      System.out.println(comp);
    } else {
      System.out.println("Error while creating the computer");
    }
    showHelp();
  }

  /**
   * CLI to show the details of a computer.
   */
  private void showComputerDetails() {
    System.out.println(
        "Please enter the id of the computer you want to see, or type q to go back to the menu");
    String input = sc.nextLine();
    if (input.equals("q")) {
      showHelp();
    }
    try {
      int id = Integer.parseInt(input);
      ComputerDto computer = webserviceComputer.get(id);
      System.out.println(computer);
      showHelp();
    } catch (NumberFormatException e) {
      System.out.println("The id you entered is not a number, please type it again");
      showComputerDetails();
    }
  }

  /**
   * CLI to list all companies available in the database.
   */
  private void listCompanies() {
    List<CompanyDto> companies = webserviceCompany.list();
    for (CompanyDto company : companies) {
      System.out.println(company);
    }
    showHelp();
  }

  /**
   * CLI to list all computers available in the database.
   */
  private void listComputer() {
    Page pageComputer = new Page(1, pageComputerSize, "");
    List<ComputerDto> computersPage;
    while (true) {
      computersPage = webserviceComputer.list(pageComputer);
      for (ComputerDto computer : computersPage) {
        System.out.println(computer);
      }
      System.out.println();
      System.out.println(
          "Type next if you want to see the next 10 computers, prev if you want to see the 10 "
              + "previous computers, and q if you want to go back to the menu");
      String input = sc.nextLine();
      switch (input) {
        case "q":
          showHelp();
          break;
        case "prev":
          pageComputer.setPage(pageComputer.getPreviousPage());
          break;
        case "next":
          pageComputer.setPage(pageComputer.getNextPage());
          break;
        default:
          System.out.println("Input incorrect");
          break;
      }
    }
  }
}
