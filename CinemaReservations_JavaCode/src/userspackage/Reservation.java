package userspackage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import cinemacomponents.*;

public class Reservation {
	
	private Cinema cinema;
	private Provoli provoli;
	private int numberOfSeats;
	private LocalDate reservationDate;
	
	// Constructor
	public Reservation(Film film, Cinema cinema, Provoli provoli, int numberOfSeats, LocalDate reservationDate) {
		setCinema(cinema);
		setProvoli(provoli);
		setNumberOfSeats(numberOfSeats);
		setReservationDate(reservationDate);
		
		System.out.println("Your reservation for " + film.getFilmTitle() + " on " + getReservationDate() + " was successful!");
	}
	
	// Getters
	public Cinema getCinema() { return this.cinema; }
	public Provoli getProvoli() { return this.provoli; }
	public int getNumberOfSeats() { return this.numberOfSeats; }
	public String getReservationDate() { return this.reservationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); }
	
	// Setters
	public void setCinema(Cinema cinema) { this.cinema = cinema; }
	public void setProvoli(Provoli provoli) { this.provoli = provoli; }
	public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }
	public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
}
