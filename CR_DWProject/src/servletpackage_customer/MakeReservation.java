package servletpackage_customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auxpackage.CookieManager;
import cinemacomponents.Film;
import cinemacomponents.Provoli;
import databasepackage.Database;
import userspackage.Customer;

/**
 * Servlet implementation class MakeReservation
 */
@WebServlet("/MakeReservation")
public class MakeReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = CookieManager.getCookies(request);
		if(cookies == null || !cookies[1].getValue().equals("customer")) {
			System.out.println(cookies[1].getValue());
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		Customer customer = (Customer)(Database.getUser(cookies[0].getValue(), cookies[1].getValue()));
		Film film = Database.getFilm(request.getParameter("movies"));

		out.println(
				"<!DOCTYPE html>"+
						"<html>"+
						"<head>"+
						"<title>View Provoles</title>"+
						"<style>"+
						"body{"+
						"  background-color: #1A1A1D;"+
						"  color: #EEF4ED;"+
						"  font-size:18px;"+
						"}"+
						"input, select{"+
						"  background-color: #950740;"+
						  "color: white;"+
						"  text-align: center;"+
						"  border: none;"+
						"  height: 25px;"+
						"  margin-top: 5px;"+
						"  margin-bottom: 5px;"+
						"}"+
						"textarea{"+
						"  background-color: #4E4E50;"+
						"  color:white;"+
						"  text-indent: 5px;"+
						"}"+
						".Buttons:hover {background-color: #950740; cursor: pointer;}"+
						".Buttons{background-color: #C3073F; color: white; padding: 10px; font-size: 14px; border: none; margin-bottom: 80px; width:auto; height: auto;}"+
						"form{margin-left:30px;}"+
						"h1{border: none; padding: 2%; color:white; background-color: #6F2232; min-height: 20px; font-size: 33px;}"+
						"table{width:50%; border-collapse: collapse; text-align:left; border: 2px solid; margin-left:15px;}"+
						"th{background-color: #6F2232; padding-top:10px; padding-bottom: 10px; border: 1px solid white; text-align: center;}"+
						"td{border: 1px solid white; text-indent: 5px;}"+
						"tr{border: 1px solid;}"+
						"</style>"+
						"</head>"+
						"<body style='margin-left:20px;'>"+
						"<h1>Available Provoles for: " + film.getFilmTitle() + " </h1>"+
						"<table style='width:50%; border-collapse: collapse; text-align:left; border: 2px solid;'>"+
						"  <tr style='border: 1px solid;'>"+
						"    <th>Provoli ID</th>"+
						"    <th>Cinema</th>"+
						"    <th>Start Date</th>"+
						"    <th>End Date</th>"+
						"    <th>Available Seats</th>"+
						"  </tr>"
						);
		ArrayList<Provoli> provoles = Database.getProvolesForFilm(film);
		for(Provoli provoli: provoles) {
			if(provoli.getProvoliIsAvailable()) {
				out.println("  <tr style='border: 1px solid;'>");
				out.println("<td>" + provoli.getProvoliID() + "</td>");
				out.println("<td>" + provoli.getProvoliCinema().getCinemaID() + "</td>");
				out.println("<td>" + provoli.getProvoliStartDate().toString() + "</td>");
				out.println("<td>" + provoli.getProvoliEndDate() + "</td>");
				out.println("<td>" + provoli.calculateAvailableSeats() + "</td>");
				out.println("  </tr>");
			}
		}
		out.println(
						"</table>"+
						"<br><br>"+
						"<form method='post' action='reserveSeats'>"+
						"  Choose a Provoli ID:"+
						"  <select name='provoli'>");
						for(Provoli provoli: provoles) {
							if(provoli.getProvoliIsAvailable()) {
								out.println("<option>" + provoli.getProvoliID() + "</option>");
							}
						}
						out.println(
						"  </select>"+
						"  <br><br>"+
						"  Choose a date:"+
						"  <input type='date' name='date' id='date' min='2019-06-20'>"+
						"  <br><br>"+
						"  Choose number of tickets:"+
						"  <input type='number' name='numberOfTickets' min='1'>"+
						"  <br><br>"+
						"  <input type='Submit' value='Book it!' class='Buttons'>"+
						"</form>"+

						"<form style='margin-top:100px;width:10%;' method='post' action='LogoutServlet'>"+
						"  <input type='submit' name='logout' value='Logout' class='Buttons'>"+
						"</form>"+

						"<script>"+
						"  n =  new Date();"+
						"  y = n.getFullYear();"+
						"  m = n.getMonth() + 1;"+
						"  d = n.getDate();"+

						"  if(m < 10){"+
						"    m = '0' + m;"+
						"  }"+
						"  if(d < 10){"+
						"    d = '0' + d;"+
						"  }"+
						"  document.getElementById('date').defaultValue = y + '-' + m + '-' +  d;"+
						"</script>"+
						"</body>"+
						"</html>"
				);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
