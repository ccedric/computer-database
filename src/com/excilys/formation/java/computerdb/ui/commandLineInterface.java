package com.excilys.formation.java.computerdb.ui;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.java.computerdb.dao.implementation.CompanyDAO;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDAO;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;

/**
 * Create a command line interface to use the application
 * @author Cédric Cousseran
 *
 */
public class commandLineInterface {
	private static ComputerDAO computerDAO = null;
	private static CompanyDAO companyDAO = null;
	private static Scanner sc =  new Scanner(System.in);
	private static int pageComputerSize=10;

	public static void main(String[] args) {
		computerDAO = new ComputerDAO();
		companyDAO = new CompanyDAO();

		System.out.println("------------------------------------------");
		System.out.println("---Welcome in the computer database app---");
		System.out.println("------------------------------------------");
		showHelp();
	}

	/**
	 * Show the help menu
	 */
	public static void showHelp(){
		System.out.println();
		System.out.println("What do you want to do:");
		System.out.println("1: List computers");
		System.out.println("2: List companies");
		System.out.println("3: Show a computer details");
		System.out.println("4: Create a computer");
		System.out.println("5: Update a computer");
		System.out.println("6: Delete a computer");
		System.out.println("7: Exit the application");

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
		case "7": System.out.println("Good bye");
		sc.close();
		System.exit(0);
		break;
		default: System.out.println("Input incorrect");
		showHelp();
		}	
	}

	/**
	 * CLI to delete a computer
	 */
	private static void deleteComputer() {
		System.out.println("Please enter the id of the computer you want to delete, or type q to go back to the menu");
		String input = sc.nextLine();
		if(input.equals("q")){
			showHelp();
		}
		try{
			int id = Integer.parseInt(input);
			Computer comp = computerDAO.find(id);
			if(comp.getId()!=0){
				computerDAO.delete(comp);
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
	 * CLI to update the computer
	 */
	private static void updateComputer() {
		System.out.println("Please enter the id of the computer you want to update, or type q to go back to the menu");
		String inputId = sc.nextLine();
		if(inputId.equals("q")){
			showHelp();
		}
		Computer comp =null;
		try{
			int id = Integer.parseInt(inputId);
			comp = computerDAO.find(id);
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
					company = companyDAO.find(idManufacturer);
				}
				break;
			}catch(NumberFormatException e){
				System.out.println("The id you entered is not a number, please type it again");
			}
		}
		Timestamp timestamp= null;
		while(true){
			System.out.println("Please enter the introduced timestamp, format: yyyy-MM-dd hh:mm:ss[.fffffffff], type 0 if you don't want to add this value, or type q to go back to the menu");
			String input = sc.nextLine();
			if(input.equals("q")){
				showHelp();
			}
			if (input.equals("0")){
				timestamp=null;
				break;
			}else{
				try{
					timestamp = Timestamp.valueOf(input);
					break;
				}catch(IllegalArgumentException e){
					System.out.println("The timestamp doesn't have the right format.");
				}
			}

		}

		Timestamp timestampEnd= null;
		while(true){
			System.out.println("Please enter the discontinued timestamp, format: yyyy-MM-dd hh:mm:ss[.fffffffff], type 0 if you don't want to add this value, or type q to go back to the menu");
			String input = sc.nextLine();
			if(input.equals("q")){
				showHelp();
			}
			if (input.equals("0")){
				timestamp=null;
				break;
			}else{
				try{
					timestampEnd = Timestamp.valueOf(input);
					if (timestampEnd.before(timestamp)){
						System.out.println("The discontinued timestamp must be after the introduced timsestamp, wich is:");
						System.out.println(timestamp);
					}else{
						break;
					}
				}catch(IllegalArgumentException e){
					System.out.println("The timestamp doesn't have the right format.");
				}	
			}
		}

		comp.setName(name);
		comp.setCompany(company);
		comp.setIntroduced(timestamp);
		comp.setDiscontinued(timestampEnd);
		boolean result = computerDAO.update(comp);
		if(result){
			System.out.println("Computer update successfully");
			System.out.println(computerDAO.find(comp.getId()));
		}
		else{
			System.out.println("Error while creating the computer");
		}
		showHelp();

	}

	/**
	 * CLI to create a computer
	 */
	private static void createComputer() {
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
					company = companyDAO.find(idManufacturer);
				}
				break;
			}catch(NumberFormatException e){
				System.out.println("The id you entered is not a number, please type it again");
				showComputerDetails();
			}
		}
		Timestamp timestamp= null;
		while(true){
			System.out.println("Please enter the introduced timestamp, format: yyyy-MM-dd hh:mm:ss[.fffffffff], type 0 if you don't want to add this value, or type q to go back to the menu");
			String input = sc.nextLine();
			if(input.equals("q")){
				showHelp();
			}
			if (input.equals("0")){
				timestamp=null;
				break;
			}
			try{
				timestamp = Timestamp.valueOf(input);
				break;
			}catch(IllegalArgumentException e){
				System.out.println("The timestamp doesn't have the right format.");
			}	
		}

		Timestamp timestampEnd= null;
		while(true){
			System.out.println("Please enter the discontinued timestamp, format: yyyy-MM-dd hh:mm:ss[.fffffffff], or type 0 if you don't want to add this value, or type q to go back to the menu");
			String input = sc.nextLine();
			if(input.equals("q")){
				showHelp();
			}
			if (input.equals("0")){
				timestampEnd=null;
				break;
			}
			try{
				timestampEnd = Timestamp.valueOf(input);
				if (timestampEnd.before(timestamp)){
					System.out.println("The discontinued timestamp must be after the introduced timsestamp, wich is:");
					System.out.println(timestamp);
				}else{
					break;
				}
			}catch(IllegalArgumentException e){
				System.out.println("The timestamp doesn't have the right format.");
			}	
		}

		Computer comp = new Computer( name,company, timestamp,timestampEnd);
		int number = computerDAO.create(comp);
		if(number!=0){
			System.out.println("Computer created successfully");
			System.out.println(computerDAO.find(number));
		}
		else{
			System.out.println("Error while creating the computer");
		}
		showHelp();
	}

	/**
	 * CLI to show the details of a computer
	 */
	private static void showComputerDetails() {
		System.out.println("Please enter the id of the computer you want to see, or type q to go back to the menu");
		String input = sc.nextLine();
		if(input.equals("q")){
			showHelp();
		}
		try{
			int id = Integer.parseInt(input);
			System.out.println(computerDAO.find(id));
			showHelp();
		}catch(NumberFormatException e){
			System.out.println("The id you entered is not a number, please type it again");
			showComputerDetails();
		}
	}

	/**
	 * CLI to list all companies available in the database
	 */
	private static void listCompanies() {
		List<Company> companies = companyDAO.list();
		for (Company company : companies){
			System.out.println(company);
		}
		showHelp();
	}

	/**
	 * CLI to list all computers available in the database
	 */
	private static void listComputer() {
		List<Computer> computers = computerDAO.list();
		Page<Computer> pageComputer = new Page<Computer>(computers, pageComputerSize);
		pageComputer.setPage(1);
		List<Computer> computersPage;
		while (true){
			computersPage= pageComputer.getListForPage();
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


