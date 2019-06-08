package databasepackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.time.LocalDate;

import auxpackage.Pair;

import cinemacomponents.*;

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
					if(loginFlag)
						return new Pair<Boolean, String>(loginFlag, table);
					else
						return null;
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
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
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
	
	public synchronized static ArrayList<Film> getAllFilms(){
		ArrayList<Film> films = new ArrayList<Film>();
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM film"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			ResultSet rs = dbStatement.executeQuery();
			while(rs.next()) {
				Film temp = new Film(rs.getString("filmid"), rs.getString("filmtitle"), rs.getString("filmcategory"), rs.getString("filmdescription"));
				films.add(temp);
			}
			return films;
		}
		catch(SQLException e) {
			return null;
		}
	}
	public synchronized static ArrayList<Provoli> getFilmProvoles(Film film) {
		ArrayList<Provoli> provoles = new ArrayList<Provoli>();
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM provoli WHERE provoliFilm=" + film.getFilmID(); 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			ResultSet rs = dbStatement.executeQuery();
			while(rs.next()) {
				Provoli temp = new Provoli(rs.getString("provoliId"), getFilm(rs.getString("provoliFilm")), getCinema(rs.getString("provoliCinema")), rs.getDate("provoliStartDate").toLocalDate(), rs.getDate("provoliEndDate").toLocalDate(), rs.getInt("provoliNumberOfReservations"), rs.getBoolean("provoliIsAvailable"));
				provoles.add(temp);
			}
			return provoles;
		}
		catch(SQLException e) {
			return null;
		}
	}
	
	public synchronized static Cinema getCinema(String cinemaID) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM cinema WHERE cinemaId=" + cinemaID; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			ResultSet rs = dbStatement.executeQuery();
			return new Cinema(rs.getString("cinemaId"), rs.getBoolean("cinemaIs3D"), rs.getInt("cinemaNumberOfSeats"));
		}
		catch(SQLException e) {
			return null;
		}
	}
	
	public synchronized static Film getFilm(String filmID) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM film WHERE filmId=" + filmID; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			ResultSet rs = dbStatement.executeQuery();
			return new Film(rs.getString("filmId"), rs.getString("filmTitle"), rs.getString("filmCategory"), rs.getString("filmDescription"));
		}
		catch(SQLException e) {
			return null;
		}
	}
}
