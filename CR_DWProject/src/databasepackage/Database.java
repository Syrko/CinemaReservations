package databasepackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
			rs.next();
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
	
	public synchronized static Cinema getCinema(String cinemaID) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM cinema WHERE cinemaId=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, cinemaID);
			ResultSet rs = dbStatement.executeQuery();
			rs.next();
			return new Cinema(rs.getString("cinemaId"), rs.getBoolean("cinemaIs3D"), rs.getInt("cinemaNumberOfSeats"));
		}
		catch(SQLException e) {
			return null;
		}
	}
	
	public synchronized static Film getFilm(String filmID) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM film WHERE filmid=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, filmID);
			ResultSet rs = dbStatement.executeQuery();
			rs.next();
			return new Film(rs.getString("filmid"), rs.getString("filmtitle"), rs.getString("filmcategory"), rs.getString("filmdescription"));
		}
		catch(SQLException e) {
			System.out.println(e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return null;
		}
	}
	
	public synchronized static void UpdateProvolesAvailability() {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")){
			{
				String statement = "UPDATE provoli SET provoliIsAvailable=true WHERE provoliStartDate<=now() AND provoliEndDate>=now()";
				PreparedStatement dbStatement = db.prepareStatement(statement);
				dbStatement.executeUpdate();}
			{
				String statement = "UPDATE provoli SET provoliIsAvailable=false WHERE provoliStartDate>now() AND provoliEndDate<now()";
				PreparedStatement dbStatement = db.prepareStatement(statement);
				dbStatement.executeUpdate();
			}
		}
		catch(SQLException e) {
			System.out.println(e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return;
		}
	}
	public synchronized static ArrayList<Provoli> getProvolesForFilm(Film film){
		UpdateProvolesAvailability();
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			ArrayList<Provoli> returnList = new ArrayList<Provoli>();
			String statement = "SELECT * FROM provoli WHERE provoliFilm=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, film.getFilmID());
			ResultSet rs = dbStatement.executeQuery();
			while(rs.next()) {
				returnList.add(new Provoli(rs.getString("provoliId"), film, getCinema(rs.getString("provoliCinema")), rs.getDate("provoliStartDate").toLocalDate(), rs.getDate("provoliEndDate").toLocalDate(), rs.getInt("provoliNumberOfReservations"), rs.getBoolean("provoliIsAvailable")));
			}
			return returnList;
		}
		catch(SQLException e) {
			System.out.println(e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return null;
		}
	}
	
	public synchronized static void CreateFilm(String title, String category, String description) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			Integer id;
			{
				String statement = "SELECT filmid FROM film";
				PreparedStatement dbStatement = db.prepareStatement(statement);
				ResultSet rs = dbStatement.executeQuery();
				ArrayList<Integer> ids = new ArrayList<Integer>();
				while(rs.next()) {
					ids.add(Integer.parseInt(rs.getString("filmid").substring(2)));
				}
				Collections.sort(ids);
				Integer counter = 0;
				while(true) {
					counter++;
					if(counter>ids.size()) {
						id = (ids.get(ids.size()-1)+1);
						break;
					}
					if(!counter.equals(ids.get(counter-1))) {
						id = counter;
						break;
					}
				}
			}
			String statement = "INSERT INTO film VALUES(?, ?, ?, ?)";
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, "F-"+id.toString());
			dbStatement.setString(2, title);
			dbStatement.setString(3, category);
			dbStatement.setString(4, description);
			dbStatement.executeQuery();
		}
		catch(SQLException e) {
			System.out.println(e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return;
		}
	}
	
	public synchronized static ArrayList<Cinema> getAllCinemas(){
		ArrayList<Cinema> returnList = new ArrayList<Cinema>();
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")){
			String statement = "SELECT * FROM cinema";
			PreparedStatement dbStatement = db.prepareStatement(statement);
			ResultSet rs = dbStatement.executeQuery();
			while(rs.next()) {
				Cinema temp = new Cinema(rs.getString("cinemaid"), rs.getBoolean("cinemaIs3D"), rs.getInt("cinemaNumberOfSeats"));
				returnList.add(temp);
			}
			return returnList;
		}
		catch(SQLException e) {
			System.out.println(e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return null;
		}
	}
	
	public synchronized static void CreateProvoli(Film film, Cinema cinema, LocalDate start, LocalDate end, Integer numOfReservations, Boolean isAvailable) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")){
			
			
			Integer id;
			{
				String statement = "SELECT provoliid FROM provoli";
				PreparedStatement dbStatement = db.prepareStatement(statement);
				ResultSet rs = dbStatement.executeQuery();
				ArrayList<Integer> ids = new ArrayList<Integer>();
				while(rs.next()) {
					ids.add(Integer.parseInt(rs.getString("provoliid").substring(2)));
				}
				Collections.sort(ids);
				Integer counter = 0;
				while(true) {
					counter++;
					if(counter>ids.size()) {
						id = (ids.get(ids.size()-1)+1);
						break;
					}
					if(!counter.equals(ids.get(counter-1))) {
						id = counter;
						break;
					}
				}		
			}
			
			String statement = "INSERT INTO provoli VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, "P-"+id.toString());
			dbStatement.setString(2, film.getFilmID());
			dbStatement.setString(3, cinema.getCinemaID());
			dbStatement.setDate(4, java.sql.Date.valueOf(start));
			dbStatement.setDate(5, java.sql.Date.valueOf(end));
			dbStatement.setInt(6, numOfReservations);
			dbStatement.setBoolean(7, isAvailable);
			dbStatement.executeQuery();
			
			UpdateProvolesAvailability();
		}
		catch(SQLException e) {
			System.out.println(e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return;
		}
	}
	
	public synchronized static void deleteFilm(Film film) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "DELETE FROM film WHERE filmid=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, film.getFilmID());
			ResultSet rs = dbStatement.executeQuery();
		}
		catch(SQLException e) {
			System.out.println("Exception: " + e.getCause());
			return;
		}
	}
}
