package userspackage;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import cinemacomponents.*;

// Class for ContentAdmin type of users
// They are responsible for managing the films
public class ContentAdmin extends User {
	
	private Scanner input;
	
	// Constructor
	public ContentAdmin(String name, String username, String password) {
		super(name, username, password);
		input = new Scanner(System.in);
		System.out.println("Content Admin " + getUsername() + " created succesffuly!");
	}
	
	public void insertFilm(Film filmToInsert) {
		//TODO add film to films' data base
		System.out.println("Film added succesfully!");
	}
	
	public void deleteFilm(Film filmToDelete) {
		//TODO remove film from films' data base
		System.out.println("Film deleted succesfully!");
	}
	
	public void assignFilmToCinema(Film filmToAssign, Cinema cinema){
		// Asks the ContentAdmin for the number of provoles and creates them for the given film at the given cinema
		int numberOfProvoles;
		try {
			do {
				System.out.print("Input Number of provoles(>0) for " + filmToAssign.getFilmTitle() + " in " + cinema.getCinemaID() + ": " );
				numberOfProvoles = input.nextInt();
			}while(numberOfProvoles <= 0);
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input - Please input integers only");
			return;
		}
		catch(Exception e) {
			System.out.println("ERROR -- Assignment aborted.");
			return;
		}
		for(int i = 0; i < numberOfProvoles; i++) {
			//new Provoli()
		}
		System.out.println("Film " + filmToAssign + " assigned to cinema " + cinema + "successfuly!");
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