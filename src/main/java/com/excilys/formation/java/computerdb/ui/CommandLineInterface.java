package com.excilys.formation.java.computerdb.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.service.TimestampDiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.service.implementation.CompanyService;
import com.excilys.formation.java.computerdb.service.implementation.ComputerService;

/**
 * Create a command line interface to use the application
 * @author Cédric Cousseran
 *
 */
public class CommandLineInterface {
	private static ComputerService computerService = null;
	private static CompanyService companyService = null;
	private static Scanner sc =  new Scanner(System.in);
	private static int pageComputerSize=10;

	public static void main(String[] args) throws DatabaseConnectionException {
		computerService = new ComputerService();
		companyService = new CompanyService();

		System.out.println("------------------------------------------");
		System.out.println("---Welcome in the computer database app---");
		System.out.println("------------------------------------------");
		showHelp();
	}

	/**
	 * Show the help menu
	 * @throws DatabaseConnectionException 
	 */
	public static void showHelp() throws DatabaseConnectionException{
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
		switch(input){
		case "1": listComputer();
		break;
		case "2": listCompanies();
		break;
		case "3": showComputerDetails();
		break;
		case "4": createComputer();
		break;
		case "5": updateComputer();
		break;
		case "6": deleteComputer();
		break;
		case "7": deleteCompany();
		break;
		case "8": System.out.println("Good bye");
		sc.close();
		System.exit(0);
		break;
		default: System.out.println("Input incorrect");
		showHelp();
		}	
	}

	/**
	 * CLI to delete a computer
	 * @throws DatabaseConnectionException 
	 */
	private static void deleteComputer() throws DatabaseConnectionException {
		System.out.println("Please enter the id of the computer you want to delete, or type q to go back to the menu");
		String input = sc.nextLine();
		if(input.equals("q")){
			showHelp();
		}
		try{
			int id = Integer.parseInt(input);
			Computer comp = computerService.find(id);
			if(comp.getId()!=0){
				computerService.delete(comp);
				System.out.println("Computer successfully deleted");
			}else{
				System.out.println("This computer doesn't exist");
				deleteComputer();
			}
			showHelp();
		}catch(NumberFormatException e){
			System.out.println("The id you entered is not a number, please type it again");
			showComputerDetails();
		}
	}
	
	/**
	 * CLI to delete a company
	 * @throws DatabaseConnectionException 
	 */
	private static void deleteCompany() throws DatabaseConnectionException {
		System.out.println("Please enter the id of the company you want to delete, or type q to go back to the menu");
		String input = sc.nextLine();
		if(input.equals("q")){
			showHelp();
		}
		try{
			int id = Integer.parseInt(input);
			Company company = companyService.find(id);
			if(company.getId()!=0){
				companyService.delete(company);
				System.out.println("Company and associated computers successfully deleted");
			}else{
				System.out.println("This company doesn't exist");
				deleteComputer();
			}
			showHelp();
		}catch(NumberFormatException e){
			System.out.println("The id you entered is not a number, please type it again");
			showComputerDetails();
		}
	}


	/**
	 * CLI to update the computer
	 * @throws DatabaseConnectionException 
	 */
	private static void updateComputer() throws DatabaseConnectionException {
		System.out.println("Please enter the id of the computer you want to update, or type q to go back to the menu");
		String inputId = sc.nextLine();
		if(inputId.equals("q")){
			showHelp();
		}
		Computer comp =null;
		try{
			int id = Integer.parseInt(inputId);
			comp = computerService.find(id);
			if (comp.getId()!=0){
				System.out.println("Computer selected:");
				System.out.println(comp);
			}else{
				System.out.println("This computer doesn't exist");
				updateComputer();
			}
		}catch(NumberFormatException e){
			System.out.println("The id you entered is not a number, please type it again");
			updateComputer();
		}

		System.out.println("Please enter the new name of the computer, or type q to go back to the menu");
		String name = sc.nextLine();
		if(name.equals("q")){
			showHelp();
		}
		Company company =null;
		while(true){
			try{
				System.out.println("Please enter the id of the manufacturer of the computer, type 0 if you don't want to add this value, or type q to go back to the menu");
				String input = sc.nextLine();
				if(input.equals("q")){
					showHelp();
				}
				if (input.equals("0")){
					company = null;
				}else{
					int idManufacturer = Integer.parseInt(input);
					company = companyService.find(idManufacturer);
				}
				break;
			}catch(NumberFormatException e){
				System.out.println("The id you entered is not a number, please type it again");
			}
		}
		LocalDate timestamp= null;
		while(true){
			System.out.println("Please enter the introduced timestamp, format: yyyy-MM-dd, type 0 if you don't want to add this value, or type q to go back to the menu");
			String input = sc.nextLine();
			if(input.equals("q")){
				showHelp();
			}
			if (input.equals("0")){
				timestamp=null;
				break;
			}else{
				try{
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					timestamp = LocalDate.parse(input,formatter);
					break;
				}catch(DateTimeParseException e){
					System.out.println("The timestamp doesn't have the right format.");
				}
			}

		}

		LocalDate timestampEnd= null;
		while(true){
			System.out.println("Please enter the discontinued timestamp, format: yyyy-MM-dd, type 0 if you don't want to add this value, or type q to go back to the menu");
			String input = sc.nextLine();
			if(input.equals("q")){
				showHelp();
			}
			if (input.equals("0")){
				timestamp=null;
				break;
			}else{
				try{
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					timestampEnd = LocalDate.parse(input,formatter);
					if (timestampEnd.isBefore(timestamp)){
						System.out.println("The discontinued timestamp must be after the introduced timsestamp, wich is:");
						System.out.println(timestamp);
					}else{
						break;
					}
				}catch(DateTimeParseException e){
					System.out.println("The timestamp doesn't have the right format.");
				}	
			}
		}

		comp.setName(name);
		comp.setCompany(company);
		comp.setIntroduced(timestamp);
		comp.setDiscontinued(timestampEnd);
		boolean result=false;
		try {
			result = computerService.update(comp);
		} catch (TimestampDiscontinuedBeforeIntroducedException e) {
			System.out.println("Update not successful, the discontinued timestamp was before the introduced timestamp");
			e.printStackTrace();
		}
		if(result){
			System.out.println("Computer update successfully");
			System.out.println(computerService.find(comp.getId()));
		}
		else{
			System.out.println("Error while creating the computer");
		}
		showHelp();

	}

	/**
	 * CLI to create a computer
	 * @throws DatabaseConnectionException 
	 */
	private static void createComputer() throws DatabaseConnectionException {
		System.out.println("Please enter the name of the computer you want to create, or type q to go back to the menu");
		String name = sc.nextLine();
		if(name.equals("q")){
			showHelp();
		}
		Company company =null;
		while(true){
			try{
				System.out.println("Please enter the id of the manufacturer of the computer, type 0 if you don't want to add this value, or type q to go back to the menu");
				String input = sc.nextLine();
				if(input.equals("q")){
					showHelp();
				}
				if (input.equals("0")){
					company = null;
				}{
					int idManufacturer = Integer.parseInt(input);
					company = companyService.find(idManufacturer);
				}
				break;
			}catch(NumberFormatException e){
				System.out.println("The id you entered is not a number, please type it again");
				showComputerDetails();
			}
		}
		LocalDate timestamp= null;
		while(true){
			System.out.println("Please enter the introduced timestamp, format: yyyy-MM-dd, type 0 if you don't want to add this value, or type q to go back to the menu");
			String input = sc.nextLine();
			if(input.equals("q")){
				showHelp();
			}
			if (input.equals("0")){
				timestamp=null;
				break;
			}
			try{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				timestamp = LocalDate.parse(input,formatter);
				break;
			}catch(DateTimeParseException e){
				System.out.println("The timestamp doesn't have the right format.");
			}	
		}

		LocalDate timestampEnd= null;
		while(true){
			System.out.println("Please enter the discontinued timestamp, format: yyyy-MM-dd, or type 0 if you don't want to add this value, or type q to go back to the menu");
			String input = sc.nextLine();
			if(input.equals("q")){
				showHelp();
			}
			if (input.equals("0")){
				timestampEnd=null;
				break;
			}
			try{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				timestampEnd = LocalDate.parse(input,formatter);
				if (timestampEnd.isBefore(timestamp)){
					System.out.println("The discontinued timestamp must be after the introduced timsestamp, wich is:");
					System.out.println(timestamp);
				}else{
					break;
				}
			}catch(DateTimeParseException e){
				System.out.println("The timestamp doesn't have the right format.");
			}	
		}

		Computer comp = new Computer.ComputerBuilder( name).company(company).introduced(timestamp).discontinued(timestampEnd).build();
		int number = 0;
		try {
			number = computerService.create(comp);
		} catch (TimestampDiscontinuedBeforeIntroducedException e) {
			System.out.println("Creation not successful, the discontinued timestamp was before the introduced timestamp");
			e.printStackTrace();
		}
		if(number!=0){
			System.out.println("Computer created successfully");
			System.out.println(computerService.find(number));
		}
		else{
			System.out.println("Error while creating the computer");
		}
		showHelp();
	}

	/**
	 * CLI to show the details of a computer
	 * @throws DatabaseConnectionException 
	 */
	private static void showComputerDetails() throws DatabaseConnectionException {
		System.out.println("Please enter the id of the computer you want to see, or type q to go back to the menu");
		String input = sc.nextLine();
		if(input.equals("q")){
			showHelp();
		}
		try{
			int id = Integer.parseInt(input);
			System.out.println(computerService.find(id));
			showHelp();
		}catch(NumberFormatException e){
			System.out.println("The id you entered is not a number, please type it again");
			showComputerDetails();
		}
	}

	/**
	 * CLI to list all companies available in the database
	 * @throws DatabaseConnectionException 
	 */
	private static void listCompanies() throws DatabaseConnectionException {
		List<Company> companies = companyService.list();
		for (Company company : companies){
			System.out.println(company);
		}
		showHelp();
	}

	/**
	 * CLI to list all computers available in the database
	 * @throws DatabaseConnectionException 
	 */
	private static void listComputer() throws DatabaseConnectionException {
		Page pageComputer = new Page(1,pageComputerSize,"");
		List<Computer> computersPage;
		while (true){
			computersPage= computerService.listPage(pageComputer);
			for (Computer computer : computersPage){
				System.out.println(computer);
			}
			System.out.println();
			System.out.println("Type next if you want to see the next 10 computers, prev if you want to see the 10 previous computers, and q if you want to go back to the menu");
			String input = sc.nextLine();
			switch(input){
			case "q":showHelp();
			break;
			case "prev":pageComputer.setPage(pageComputer.getPreviousPage());
			break;
			case "next":pageComputer.setPage(pageComputer.getNextPage());
			break;
			default: System.out.println("Input incorrect");
			break;
			}
		}
	}
}


