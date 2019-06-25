package servletpackage_customer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import auxpackage.CookieManager;
import userspackage.*;
import cinemacomponents.*;
import databasepackage.Database;

/**
 * Servlet implementation class ViewReservations
 */
@WebServlet("/ViewReservations")
public class ViewReservations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewReservations() {
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
		
		out.println(
				"<!DOCTYPE html>"+
						"<html>"+
						"<head>"+
						"<title>Your Reservations</title>"+
						"</head>"+
						"<body style='margin-left:20px;'>"+
						"<h1>Your Reservations:</h1>"+
						"<table style='width:50%; border-collapse: collapse; text-align:left; border: 2px solid;'>"+
						"  <tr style='border: 1px solid;'>"+
						"    <th>Reservation ID</th>"+
						"    <th>Film</th>"+
						"    <th>Cinema</th>"+
						"    <th>Date</th>"+
						"    <th># Of Seats</th>"+
						"  </tr>");
		ArrayList<Reservation> reservations = Database.getReservationsForCustomer(customer.getUsername());
		for(Reservation reservation: reservations) {
			
			out.println("  <tr style='border: 1px solid;'>");
			out.println("<td>" + reservation.getReservationID() + "</td>");
			out.println("<td>" + reservation.getProvoli().getProvoliFilm().getFilmID() + "</td>");
			out.println("<td>" + reservation.getProvoli().getProvoliCinema().getCinemaID() + "</td>");
			out.println("<td>" + reservation.getReservationDate() + "</td>");
			out.println("<td>" + reservation.getNumberOfSeats() + "</td>");
			out.println("  </tr>");
		}
		out.println(
						"</table>"+
						"<br><br>"+
						"<form method='post' action='CustomerServlet'>"+
						"  <input type='submit' value='Return'>"+
						"</form>"+
						"<form style='margin-top:100px;width:10%;' method='post' action='LogoutServlet'>"+
						"  <input type='submit' name='logout' value='Logout'>"+
						"</form>"+
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
