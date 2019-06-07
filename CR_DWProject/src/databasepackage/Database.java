package databasepackage;

import auxpackage.Pair;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Database {

	public synchronized static Pair<Boolean, String> ValidateCredentials(String username, String password) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservations", "admin", "admin")) {
			String[] tables = {"admin", "contentAdmin", "customer"}; 
			
			for(String table : tables) {
				String statement = "SELECT username FROM " + table;
				PreparedStatement dbStatement = db.prepareStatement(statement);
				ResultSet rs = dbStatement.executeQuery();
				
				String validUsername = null;
				while(rs.next()) {
					String tempUsername = rs.getString("username");
					if(username == tempUsername) {
						validUsername = tempUsername;
						break;
					}
				}
				if(validUsername != null) {
					statement = "SELECT password FROM " + table + "WHERE username=?";
					dbStatement = db.prepareStatement(statement);
					dbStatement.setString(1, validUsername);
					rs = dbStatement.executeQuery();
					
					Boolean loginFlag = false;
					while(rs.next()) {
						if(password == rs.getString("password")) {
							loginFlag = true;
							break;
						}
					}
					return new Pair<Boolean, String>(loginFlag, table);
				}
			}
		}
		catch(SQLException e) {
			return new Pair<Boolean, String>(false, "SQL exception code: " + e.getErrorCode());
		}
		catch(Exception e) {
			return new Pair<Boolean, String>(false, e.getCause().toString());
		}
		return null;
	}
}
