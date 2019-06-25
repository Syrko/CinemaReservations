package userspackage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import cinemacomponents.*;

public class Reservation {
	
	private Provoli provoli;
	private int numberOfSeats;
	private String reservationID;
	private LocalDate reservationDate;
	private Customer customer;
	
	// Constructor
	public Reservation(String reservationID, Provoli provoli, int numberOfSeats, LocalDate reservationDate, Customer customer) {
		
		setReservationID(reservationID);
		setProvoli(provoli);
		setNumberOfSeats(numberOfSeats);
		setCustomer(customer);
		setReservationDate(reservationDate);
		
		System.out.println("Your reservation for " + provoli.getProvoliFilm().getFilmTitle() + " on " + provoli.getProvoliStartDate() + " was successful!");
	}
	
	// Getters
	public Provoli getProvoli() { return this.provoli; }
	public int getNumberOfSeats() { return this.numberOfSeats; }
	public String getReservationID() { return this.reservationID; }
	public LocalDate getReservationDate() { return this.reservationDate; }
	public Customer getCustomer() { return this.customer; }
	
	// Setters
	public void setProvoli(Provoli provoli) { this.provoli = provoli; }
	public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }
	public void setReservationID(String reservationID) { this.reservationID = reservationID; }
	public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
	public void setCustomer(Customer customer) { this.customer = customer; }
}
