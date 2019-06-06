package mainpackage;

import java.time.LocalDate;
import userspackage.*;
import cinemacomponents.*;

public class MainClass {
	
	public static void main(String[] args) {
		// Creates Customer, Admin, ContentAdmin users and saves them to their respective txt file
		Customer customer = new Customer("Nikolas", "Tollis", "123");
		customer.writeToFile();
		Admin admin = new Admin("Kostas", "Syrios", "****");
		admin.writeToFile();
		ContentAdmin contentAdmin = new ContentAdmin("Makis", "Papadopoulos", "fpmm");
		contentAdmin.writeToFile();
		
		// Creates Film, Cinema, Provoli objects and saves them to their respective txt file
		Film film = new Film("f05445", "IT", "Horror", "Scary clown eats naughty children and gives them ballons.");
		film.writeToFile();
		Cinema cinema = new Cinema("c72362", false, 100);
		cinema.writeToFile();
		Provoli provoli = new Provoli("p71368", film, cinema, LocalDate.of(2019, 04, 05), LocalDate.of(2019, 04, 25));
		provoli.writeToFile();
	}

}
