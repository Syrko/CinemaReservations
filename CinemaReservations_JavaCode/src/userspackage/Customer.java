package userspackage;

import java.io.FileWriter;
import java.io.PrintWriter;
import cinemacomponents.Film;

public class Customer extends User {

	private Reservation reservation;
	
	// Constructor
	public Customer(String name, String username, String password) {
		super(name, username, password);
		System.out.println("Customer " + getUsername() + " created succesffuly!");
	}
	
	public void showAvailableFilms(){
		//TODO add functionality
		System.out.println("Here are the films!");
	}
	
	public void makeReservation(Film film, int numberOfSeats) {
		//TODO add functionality
		//reservation = new Reservation();
		System.out.println("Reservation made for " + numberOfSeats + " in " + film.getFilmTitle());
	}
	
	public void viewReservation() {
		//TODO add functionality
		System.out.println("Here is your Reservation!");
	}
	
	public void writeToFile() {
		try {
			FileWriter fileWriter = new FileWriter(this.getClass().getSimpleName() + ".txt", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println("Name: " + getName());
			printWriter.println("Username: " + getUsername());
			printWriter.println("Password: " + getPassword());
			printWriter.println("=============================");
			printWriter.close();
		}
		catch(Exception e) {
			
		}
	}
}
