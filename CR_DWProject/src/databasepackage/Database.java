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
import userspackage.*;

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
			System.out.println("getFILM: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return null;
		}
	}
	
	public synchronized static void UpdateProvolesAvailability() {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")){
			{
				String statement = "UPDATE provoli SET provoliIsAvailable=true WHERE provoliEndDate>=now()";
				PreparedStatement dbStatement = db.prepareStatement(statement);
				dbStatement.executeUpdate();}
			{
				String statement = "UPDATE provoli SET provoliIsAvailable=false WHERE provoliEndDate<now()";
				PreparedStatement dbStatement = db.prepareStatement(statement);
				dbStatement.executeUpdate();
			}
		}
		catch(SQLException e) {
			System.out.println("ProvUpdAv: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
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
			System.out.println("getProvForFilm: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
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
				if(ids.isEmpty()) {
					id = 1;
				}
				else {
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
			}
			String statement = "INSERT INTO film VALUES(?, ?, ?, ?)";
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, "F-"+id.toString());
			dbStatement.setString(2, title);
			dbStatement.setString(3, category);
			dbStatement.setString(4, description);
			dbStatement.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("Create film: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
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
			System.out.println("GetAllCinemas: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
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
				if(ids.isEmpty()) {
					id=1;
				}
				else {
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
			dbStatement.executeUpdate();
			
			UpdateProvolesAvailability();
		}
		catch(SQLException e) {
			System.out.println("CreateProvoli: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return;
		}
	}
	
	public synchronized static int deleteFilm(Film film) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "DELETE FROM film WHERE filmid=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, film.getFilmID());
			dbStatement.executeUpdate();
			return 0;
		}
		catch(SQLException e) {
			System.out.println("Exception DeleteFilm: " + e.getCause() + " -- " + e.getMessage() + " -- " + " -- " + e.getSQLState() + " -- " + e.getLocalizedMessage());
			if(e.getErrorCode()==0) {
				return 1;
			}
			return -1;
		}
	}
	
	public synchronized static void deleteProvoli(Provoli prov) {
		UpdateProvolesAvailability();
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "DELETE FROM provoli WHERE provoliid=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, prov.getProvoliID());
			dbStatement.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("Exception DeleteProvoli: " + e.getCause() + " -- " + e.getMessage() + " -- " + e.getSQLState() + " -- " + e.getLocalizedMessage()+  " -- " + e.getNextException().toString() );
			return;
		}
	}
	
	public synchronized static Provoli getProvoli(String provoliID) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM provoli WHERE provoliid=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, provoliID);
			ResultSet rs = dbStatement.executeQuery();
			rs.next();
			return new Provoli(rs.getString("provoliid"),Database.getFilm(rs.getString("provolifilm")), Database.getCinema(rs.getString("provolicinema")), rs.getDate("provoliStartDate").toLocalDate(), rs.getDate("provoliEndDate").toLocalDate(), rs.getInt("provoliNumberOfReservations"), rs.getBoolean("provoliIsAvailable"));
		}
		catch(SQLException e) {
			System.out.println("GetProvoli: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return null;
		}
	}
	
	public synchronized static String userExists(String username) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM contentadmin WHERE username=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, username);
			ResultSet rs = dbStatement.executeQuery();
			if(rs.next()) {
				return "contentadmin";
			}
			else {
				statement = "SELECT * FROM customer WHERE username=?"; 
				dbStatement = db.prepareStatement(statement);
				dbStatement.setString(1, username);
				rs = dbStatement.executeQuery();
				if(rs.next()) {
					return "customer";
				}
				else
					return "&no_user";
			}
		}
		catch(SQLException e) {
			System.out.println("userExists: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return null;
		}
	}
	
	public synchronized static boolean CreateUser(User user, String usertype) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			if(usernameExists(user.getUsername())){
				return false;
			}
			
			String statement = "INSERT INTO " + usertype + " VALUES(?, ?, ?)"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, user.getName());
			dbStatement.setString(2, user.getUsername());
			dbStatement.setString(3, user.getPassword());
			dbStatement.executeUpdate();
			return true;
		}
		catch(SQLException e) {
			System.out.println("CreateUser: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return false;
		}
		
	}
	
	public synchronized static void DeleteUser(User user, String usertype) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			{
				String statement = "DELETE FROM reservation WHERE customer=?"; 
				PreparedStatement dbStatement = db.prepareStatement(statement);
				dbStatement.setString(1, user.getUsername());
				dbStatement.executeUpdate();
			}
			String statement = "DELETE FROM " + usertype + " WHERE username=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, user.getUsername());
			dbStatement.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("DeleteUser: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return;
		}
		
	}
	
	public synchronized static boolean EditUser(User user, String oldUsername, String usertype) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
						
			{
				String statement = "UPDATE reservation SET customer=? WHERE customer=?"; 
				PreparedStatement dbStatement = db.prepareStatement(statement);
				dbStatement.setString(1, user.getUsername());
				dbStatement.setString(2, oldUsername);
				dbStatement.executeUpdate();
			}
			String statement = "UPDATE " + usertype + " SET name=?,username=?,password=? WHERE username=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, user.getName());
			dbStatement.setString(2, user.getUsername());
			dbStatement.setString(3, user.getPassword());
			dbStatement.setString(4, oldUsername);
			dbStatement.executeUpdate();
			return true;
		}
		catch(SQLException e) {
			System.out.println("EditUser: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return false;
		}
		
	}
	
	public synchronized static User getUser(String username, String usertype) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT * FROM " + usertype + " WHERE username=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, username);
			ResultSet rs = dbStatement.executeQuery();
			rs.next();
			if(usertype.equals("customer")) {
				return new Customer(rs.getString("name"), rs.getString("username"), rs.getString("password"));
			}
			else if(usertype.equals("contentadmin")){
				return new ContentAdmin(rs.getString("name"), rs.getString("username"), rs.getString("password"));
			}
			else
				return null;
		}
		catch(SQLException e) {
			System.out.println("GetUser: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return null;
		}
	}
	
	public synchronized static boolean usernameExists(String username) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			String statement = "SELECT username FROM(SELECT username FROM ((SELECT username AS username FROM admin) UNION (SELECT username AS username FROM customer) UNION (SELECT username AS username FROM contentadmin))as results) as results WHERE username=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, username);
			ResultSet rs = dbStatement.executeQuery();
			return rs.next();
		}
		catch(SQLException e) {
			System.out.println("UsernameExists: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return false;
		}
		
	}
	
	public synchronized static ArrayList<Reservation> getReservationsForCustomer(String customerID) {
		deleteExpiredReservations();
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {
			ArrayList<Reservation> returnList = new ArrayList<Reservation>();
			
			String statement = "SELECT * FROM reservation WHERE customer=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, customerID);
			ResultSet rs = dbStatement.executeQuery();
			
			while(rs.next()) {
				returnList.add(new Reservation(rs.getString("reservationID"), getProvoli(rs.getString("provoli")), rs.getInt("numberofseats"), rs.getDate("reservationDate").toLocalDate(), (Customer)getUser(rs.getString("customer"), "customer")));
			}
			return returnList;
		}
		catch(SQLException e) {
			System.out.println("getReservationsForCustomer: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return null;
		}
	}
	
	public synchronized static void makeReservation(String provoliID, int NumberOfSeats, LocalDate date, String customerUsername) {
		deleteExpiredReservations();
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {			
			Integer reservationID;
			{
				String statement = "SELECT reservationid FROM reservation";
				PreparedStatement dbStatement = db.prepareStatement(statement);
				ResultSet rs = dbStatement.executeQuery();
				ArrayList<Integer> ids = new ArrayList<Integer>();
				
				while(rs.next()) {
					ids.add(Integer.parseInt(rs.getString("reservationid").substring(2)));
				}
				if(ids.isEmpty())
					reservationID = 1;
				else {
				Collections.sort(ids);
				Integer counter = 0;
				while(true) {
					counter++;
					if(counter>ids.size()) {
						reservationID = (ids.get(ids.size()-1)+1);
						break;
					}
					if(!counter.equals(ids.get(counter-1))) {
						reservationID = counter;
						break;
					}
				}		
				}
			}
			
			String statement = "INSERT INTO reservation VALUES(?, ?, ?, ?, ?)"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, "R-" + reservationID.toString());
			dbStatement.setString(2, provoliID);
			dbStatement.setInt(3, NumberOfSeats);
			dbStatement.setDate(4, java.sql.Date.valueOf(date));
			dbStatement.setString(5, customerUsername);
			dbStatement.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("makeReservation: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return;
		}
	}
	
	public synchronized static void deleteExpiredReservations() {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {			
			String statement = "DELETE FROM reservation WHERE reservationdate<=now()-interval '1 day'"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("deleteExpiredReservation: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return;
		}
	}
	
	public synchronized static int getReservedSeats(Provoli provoli) {
		try(Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinemaReservationsDB", "postgres", "admin")) {			
			String statement = "SELECT SUM(numberofseats) AS res_seats FROM reservation WHERE provoli=?"; 
			PreparedStatement dbStatement = db.prepareStatement(statement);
			dbStatement.setString(1, provoli.getProvoliID());
			ResultSet rs = dbStatement.executeQuery();
			if(rs.next()) {
				return rs.getInt("res_seats");
			}
			else
				return -200000;
		}
		catch(SQLException e) {
			System.out.println("getReservedSeats: " + e.getErrorCode() + " -- "+ e.getCause() + " -- "+e.getMessage());
			return -1000000;
		}
	}
}
