package userspackage;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

// Class for Admin type of users
// They are responsible for managing all the user accounts 
public class Admin extends User{

	private Scanner input;
	
	// Constructor
	public Admin(String name, String username, String password) {
		super(name, username, password);
		input = new Scanner(System.in);
		System.out.println("Admin " + getUsername() + " created succesffuly!");
	}
	
	public void createUser() {
		// Asks admin to input account type to be created
		String userType;
		do {
			System.out.print("Input user type(admin, contentAdmin, customer): ");
			userType = input.nextLine();
		}while(!checkUserType(userType));

		// TODO store new users to appropriate data structure
		// Creates account of the specified type
		if(userType == User.ADMIN) {
			new Admin(input.nextLine(), input.nextLine(), input.nextLine());
		}
		else if(userType == User.CONTENT_ADMIN) {
			new ContentAdmin(input.nextLine(), input.nextLine(), input.nextLine());
		}
		else if(userType == User.CUSTOMER) {
			new Customer(input.nextLine(), input.nextLine(), input.nextLine());
		}
	}
	
	public void updateUser(User userToUpdate) {
		//TODO add more functionality
		//TODO possible change-user-type functionality
		System.out.println("User updated succesfully!");
	}
	
	public void deleteUser(User userToDelete) {
		//TODO add deletion functionality
		// Removal from user-storing data structure
		System.out.println("User deleted succesfully!");
	}
	
	public void searchUser(User userToFind) {
		//TODO add searching functionality
		// Search in user-storing data structure
		System.out.println("Here is the user you searched for");
	}
	
	public void viewAllUsers() {
		//TODO show all users stores in user-storing data structure
		System.out.println("Here are all the users!");
	}
	
	// Checks if the given user type exists in data base
	private boolean checkUserType(String userType) {
		if(User.USER_TYPES.contains(userType)) {
			return true;
		}
		else {
			System.out.println("Error: User type specified does not exist! Try again...");
			return false;
		}
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