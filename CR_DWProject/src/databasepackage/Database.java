package databasepackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

	public synchronized static boolean ValidateCredentials(String username, String password) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservations", "admin", "admin")) {
			
		}
		catch(SQLException e) {
			
		}
	}
}
