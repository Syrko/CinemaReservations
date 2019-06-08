package cinemacomponents;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Provoli {
	
	private String provoliID;
	private Film provoliFilm;
	private Cinema provoliCinema;
	private LocalDate provoliStartDate;
	private LocalDate provoliEndDate;
	private int provoliNumberOfReservations;
	private boolean provoliIsAvailable;
	
	// Constructor
	public Provoli(String provoliID, Film provoliFilm, Cinema provoliCinema, LocalDate provoliStartDate, LocalDate provoliEndDate) {
		setProvoliID(provoliID);
		setProvoliFilm(provoliFilm);
		setProvoliCinema(provoliCinema);
		setProvoliStartDate(provoliStartDate);
		setProvoliEndDate(provoliEndDate);
		
		updateProvoliAvailability();
		
		System.out.println("Provoli created successfully with: ");
		System.out.println("ID: " + getProvoliID());
		System.out.println("Film: " + getProvoliFilm().getFilmTitle() + " (" + getProvoliFilm().getFilmID() + ")");
		System.out.println("Cinema : " + getProvoliCinema().getCinemaID());
		System.out.println("From " + getProvoliStartDate() + " to " + getProvoliEndDate());
		System.out.println("Availability: " + getProvoliIsAvailable());
	}
	
	public Provoli(String provoliID, Film provoliFilm, Cinema provoliCinema, LocalDate provoliStartDate, LocalDate provoliEndDate, int numberOfReservations, boolean provoliIsAvailable) {
		setProvoliID(provoliID);
		setProvoliFilm(provoliFilm);
		setProvoliCinema(provoliCinema);
		setProvoliStartDate(provoliStartDate);
		setProvoliEndDate(provoliEndDate);
		setProvoliNumberOfReservations(numberOfReservations);
		setProvoliIsAvailable(provoliIsAvailable);
	}
	
	public void writeToFile() {
		try {
			FileWriter fileWriter = new FileWriter(this.getClass().getSimpleName() + ".txt", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println("ID: " + getProvoliID());
			printWriter.println("Film: " + getProvoliFilm().getFilmTitle() + " (" + getProvoliFilm().getFilmID() + ")");
			printWriter.println("Cinema : " + getProvoliCinema().getCinemaID());
			printWriter.println("From " + getProvoliStartDate() + " to " + getProvoliEndDate());
			
			updateProvoliAvailability();
			
			printWriter.println("Availability: " + getProvoliIsAvailable());
			printWriter.println("=============================");
			printWriter.close();
		}
		catch(Exception e) {
			
		}
	}
	
	// Checks for provoli availability
	private void updateProvoliAvailability() {
		if(this.provoliStartDate.isBefore(LocalDate.now()) && this.provoliEndDate.isAfter(LocalDate.now()))
			this.provoliIsAvailable = true;
		else
			this.provoliIsAvailable = false;
	}
	
	// Getters
	public String getProvoliID() {return provoliID;}
	public Film getProvoliFilm() {return provoliFilm;}	
	public Cinema getProvoliCinema() {return provoliCinema;}	
	public String getProvoliStartDate() {return provoliStartDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));}	
	public String getProvoliEndDate() {return provoliEndDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));}	
	public int getProvoliNumberOfReservations() {return provoliNumberOfReservations;}	
	public boolean getProvoliIsAvailable() {return provoliIsAvailable;}
	
	// Setters
	public void setProvoliID(String provoliID) {this.provoliID = provoliID;}	
	public void setProvoliFilm(Film provoliFilm) {this.provoliFilm = provoliFilm;}	
	public void setProvoliCinema(Cinema provoliCinema) {this.provoliCinema = provoliCinema;}	
	public void setProvoliStartDate(LocalDate provoliStartDate) {this.provoliStartDate = provoliStartDate;}	
	public void setProvoliEndDate(LocalDate provoliEndDate) {this.provoliEndDate = provoliEndDate;}	
	public void setProvoliNumberOfReservations(int provoliNumberOfReservations) {this.provoliNumberOfReservations = provoliNumberOfReservations;}	
	public void setProvoliIsAvailable(boolean provoliIsAvailable) {this.provoliIsAvailable = provoliIsAvailable;}
	
	
}
