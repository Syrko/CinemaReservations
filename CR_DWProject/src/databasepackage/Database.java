package databasepackage;

import auxpackage.Pair;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Database {

	public synchronized static Pair<Boolean, String> ValidateCredentials(String username, String password) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String[] tables = {"admin", "contentadmin", "customer"}; 
			
			for(String table : tables) {
				String statement = "SELECT username FROM " + table;
				PreparedStatement dbStatement = db.prepareStatement(statement);
				ResultSet rs = dbStatement.executeQuery();
				String validUsername = null;
				
				while(rs.next()) {
					String tempUsername = rs.getString("username");
					if(username.equals(tempUsername)) {
						validUsername = tempUsername;
						break;
					}
				}
				if(validUsername != null) {
					statement = "SELECT password FROM " + table + " WHERE username=?";
					dbStatement = db.prepareStatement(statement);
					dbStatement.setString(1, validUsername);
					rs = dbStatement.executeQuery();
					
					Boolean loginFlag = false;
					while(rs.next()) {
						if(password.equals(rs.getString("password"))) {
							loginFlag = true;
							break;
						}
					}
					return new Pair<Boolean, String>(loginFlag, table);
				}
			}
			return null;
		}
		catch(SQLException e) {
			return new Pair<Boolean, String>(false, "SQL exception: " + e.getMessage());
		}
		catch(Exception e) {
			return new Pair<Boolean, String>(false, e.getCause().toString());
		}
	}
	public synchronized static String getNameOfUser(String username, String table) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservations", "admin", "admin")) {
			String statement = "SELECT name FROM " + table + " WHERE username=?";
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, username);
			ResultSet rs = dbStatement.executeQuery();
			return rs.getString("name");
		}
		catch(SQLException e) {
			return null;
		}
	}
}
