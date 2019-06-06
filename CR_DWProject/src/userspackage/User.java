package userspackage;

import java.util.HashSet;

// Parent class of all types of users of the application.
// Contains core information, such as user-name and password.
public class User {
	
	// User types for enumeration
	public static final String ADMIN = "admin";
	public static final String CONTENT_ADMIN = "contentAdmin";
	public static final String CUSTOMER = "customer";
	public static final HashSet<String> USER_TYPES;
	static {
		USER_TYPES = new HashSet<String>();
		USER_TYPES.add(ADMIN);
		USER_TYPES.add(CONTENT_ADMIN);
		USER_TYPES.add(CUSTOMER);
	}

	private String name;
	private String username;
	private String password;
	
	// Constructor
	public User(String name, String username, String password) {
		setName(name);
		setUsername(username);
		setPassword(password);
	}
	
	public boolean login(String username, String password) {
		if(getUsername() == username) {
			if(getPassword() == password) {
				System.out.println("Welcome " + getName() + "!");
				System.out.println("You are now logged in as: " + this.getClass().getSimpleName());
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public boolean logout() {
		//TODO logout functionality
		System.out.println("You have logged out!");
		return true;
	}
	
	// Getters
	public String getName() { return this.name; }
	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	
	// Setters
	public void setName(String name) { this.name = name; }
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
}
