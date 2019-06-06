package cinemacomponents;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Cinema {
	
	private String cinemaID;
	private boolean cinemaIs3D;
	private int cinemaNumberOfSeats;
	
	// Constructor
	public Cinema(String cinemaID, boolean cinemaIs3D, int cinemaNumberOfSeats) {
		setCinemaID(cinemaID);
		setCinemaIs3D(cinemaIs3D);
		setCinemaNumberOfSeats(cinemaNumberOfSeats);
		
		System.out.println("Cinema created successfully with:");
		System.out.println("ID: " + getCinemaID());
		System.out.println("Seats: " + getCinemaNumberOfSeats());
		System.out.println("3D: "+ getCinemaIs3D());
		System.out.println();
	}

	public void writeToFile() {
		try {
			FileWriter fileWriter = new FileWriter(this.getClass().getSimpleName() + ".txt", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println("ID: " + getCinemaID());
			printWriter.println("Seats: " + getCinemaNumberOfSeats());
			printWriter.println("3D: " + getCinemaIs3D());
			printWriter.println("=============================");
			printWriter.close();
		}
		catch(Exception e) {
			
		}
	}
	
	// Getters
	public String getCinemaID() {return cinemaID;}	
	public boolean getCinemaIs3D() {return cinemaIs3D;}
	public int getCinemaNumberOfSeats() {return cinemaNumberOfSeats;}
	
	// Setters
	public void setCinemaID(String cinemaID) {this.cinemaID = cinemaID;}	
	public void setCinemaIs3D(boolean cinemaIs3D) {this.cinemaIs3D = cinemaIs3D;}	
	public void setCinemaNumberOfSeats(int cinemaNumberOfSeats) {this.cinemaNumberOfSeats = cinemaNumberOfSeats;}
	
}
